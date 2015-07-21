package barqsoft.footballscores;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainScreenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>
{
    public ScoresAdapter mAdapter;
    public static final int SCORES_LOADER = 0;
    //format "yyyy-MM-dd"
    private String[] fragmentdate = new String[1];
    private int last_selected_item = -1;

    ListView score_list;

    TextView emptyTextView;

    public MainScreenFragment()
    {

    }

    @Override
    public void onResume() {
        super.onResume();
        //showMessage();
        getLoaderManager().restartLoader(SCORES_LOADER, null, MainScreenFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getLoaderManager().destroyLoader(SCORES_LOADER);
    }

    public void restartScoresLoader(){
        getLoaderManager().restartLoader(SCORES_LOADER, null, MainScreenFragment.this);
    }

    public void setFragmentDate(String date)
    {
        fragmentdate[0] = date;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        //update_scores();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        emptyTextView =  (TextView)rootView.findViewById(R.id.textView);
        score_list = (ListView) rootView.findViewById(R.id.scores_list);
        showMessage(0);
        getLoaderManager().initLoader(SCORES_LOADER, null, this);
        mAdapter = new ScoresAdapter(getActivity(),null,0);
        score_list.setAdapter(mAdapter);
        mAdapter.detail_match_id = MainActivity.selected_match_id;
        score_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ViewHolder selected = (ViewHolder) view.getTag();
                mAdapter.detail_match_id = selected.match_id;
                MainActivity.selected_match_id = (int) selected.match_id;
                mAdapter.notifyDataSetChanged();
            }
        });
        emptyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoaderManager().restartLoader(SCORES_LOADER, null, MainScreenFragment.this);
            }
        });
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(getActivity(),DatabaseContract.scores_table.buildScoreWithDate(),
                null,"date",fragmentdate,null);


    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        //Log.v(FetchScoreTask.LOG_TAG,"loader finished");
        //cursor.moveToFirst();
        /*
        while (!cursor.isAfterLast())
        {
            Log.v(FetchScoreTask.LOG_TAG,cursor.getString(1));
            cursor.moveToNext();
        }
        */
        if (cursor.getCount()==0){
            showMessage(2);
            return;
        }

        int i = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            i++;
            cursor.moveToNext();
        }
        //Log.v(FetchScoreTask.LOG_TAG,"Loader query: " + String.valueOf(i));
        mAdapter.swapCursor(cursor);
        //mAdapter.notifyDataSetChanged();
        hideMessage();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {
        mAdapter.swapCursor(null);
    }

    public void showMessage(){
        showMessage(0);
    }
    public void showMessage(int id){
        score_list.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
        String str = "";
        switch(id){
            case 0:
                str = getString(R.string.loading);
                break;
            case 1:
                str = getString(R.string.no_internet);
                break;
            case 2:
                str = getString(R.string.no_games_for_day);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
        emptyTextView.setText(str);

    }
    public void hideMessage(){
        score_list.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.GONE);
    }

}
