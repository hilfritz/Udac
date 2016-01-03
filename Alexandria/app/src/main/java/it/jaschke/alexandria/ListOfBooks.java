package it.jaschke.alexandria;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import it.jaschke.alexandria.api.BookListAdapter;
import it.jaschke.alexandria.api.Callback;
import it.jaschke.alexandria.data.AlexandriaContract;

/**
 * See CURSORLOADER USAGES
 * http://www.programcreek.com/java-api-examples/index.php?api=com.activeandroid.content.ContentProvider
 */
public class ListOfBooks extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private BookListAdapter bookListAdapter;
    private ListView bookList;
    private int position = ListView.INVALID_POSITION;
    private EditText searchText;
    private TextView emptyTextView;
    boolean emptyDatabase = false;

    private final int LOADER_ID = 10;
    private static final String TAG = "ListOfBooks";

    public ListOfBooks() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Cursor cursor = getActivity().getContentResolver().query(
                AlexandriaContract.BookEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );


        bookListAdapter = new BookListAdapter(getActivity(), cursor, 0);
        View rootView = inflater.inflate(R.layout.fragment_list_of_books, container, false);
        emptyTextView = (TextView)rootView.findViewById(R.id.emptyTextView);

        searchText = (EditText) rootView.findViewById(R.id.searchText);




        rootView.findViewById(R.id.searchButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListOfBooks.this.restartLoader();
                    }
                }
        );

        bookList = (ListView) rootView.findViewById(R.id.listOfBooks);
        bookList.setAdapter(bookListAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = bookListAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    ((Callback) getActivity())
                            .onItemSelected(cursor.getString(cursor.getColumnIndex(AlexandriaContract.BookEntry._ID)));
                }
            }
        });

        if (cursor.getCount()==0){
            emptyDatabase = true;
            showErrorMessage(getString(R.string.empty_book));
            Log.d(TAG, "onCreateView() empty database");
        }

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Your piece of code on keyboard search click
                    ListOfBooks.this.restartLoader();
                    return true;
                } else { //AUTOUPDATE THE ERROR MESSAGE IF NOT EMPTY
                    if (emptyDatabase == false) {
                        showErrorMessage(getString(R.string.search_book_with_data, searchText.getText().toString()));
                        return true;
                    }
                }
                return false;
            }
        });


        return rootView;
    }



    private void restartLoader(){
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    /**
     * @SEE https://github.com/pardom/ActiveAndroid/wiki/Using-the-content-provider
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        final String selection = AlexandriaContract.BookEntry.TITLE +" LIKE ? OR " + AlexandriaContract.BookEntry.SUBTITLE + " LIKE ? ";
        String searchString =searchText.getText().toString();

        if(searchString.length()>0){
            searchString = "%"+searchString+"%";
            return new CursorLoader(
                    getActivity(),
                    AlexandriaContract.BookEntry.CONTENT_URI,
                    null,
                    selection,
                    new String[]{searchString,searchString},
                    null
            );
        }

        return new CursorLoader(
                getActivity(),
                AlexandriaContract.BookEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        bookListAdapter.swapCursor(data);
        Log.d(TAG, "onLoadFinished() "+data.getCount());
        if (data.getCount()==0 ){
            //THIS MEANS THIS IS A SEARCH
            if (emptyDatabase){
                Log.d(TAG, "onLoadFinished() firstLoad");
                showErrorMessage(getString(R.string.empty_book));
            }else{
                showErrorMessage(getString(R.string.book_not_found2));
            }
        }else{
            emptyDatabase=false;
            hideErrorMessage();
            Log.d(TAG, "onLoadFinished() not empty");
        }

        if (position != ListView.INVALID_POSITION) {
            bookList.smoothScrollToPosition(position);
        }
    }

    private void showErrorMessage(String string){
        emptyTextView.setText(string);
        emptyTextView.setVisibility(View.VISIBLE);
        bookList.setVisibility(View.GONE);

    }
    private void hideErrorMessage(){
        emptyTextView.setVisibility(View.GONE);
        bookList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        bookListAdapter.swapCursor(null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.books);
    }
}
