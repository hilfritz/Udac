package com.hilfritz.myappportfolio.ui.places;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.activeandroid.content.ContentProvider;
import com.hilfritz.favoriteplacesmodule.model.database.PlaceDbModel;
import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.BaseFragment;
import com.hilfritz.myappportfolio.R;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hilfritz P. Camallere on 1/2/2016.
 * @see https://guides.codepath.com/android/activeandroid-guide
 * @SEE https://github.com/pardom/ActiveAndroid/wiki/Using-the-content-provider
 * - use a content provider to automatically update app on searchtext query changes
 * @see http://www.programcreek.com/java-api-examples/index.php?api=com.activeandroid.content.ContentProvider
 */
public class PlaceListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 101;
    public static final String TAG="PlaceListFragment";

    @Bind(R.id.searcView)
    SearchView searchView;

    @Bind(R.id.placeListView)
    ListView placeListView;

    PlaceListAdapter placeListAdapter;
    boolean emptyDatabase = false;
    String queryString = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_placelist, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void afterInitViews() {
        /*
        Cursor cursor = getActivity().getContentResolver().query(
                ContentProvider.createUri(PlaceDbModel.class, null),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );
        */
        Cursor cursor = PlaceDbModel.fetchResultCursor();

        placeListAdapter = new PlaceListAdapter(getActivity(),cursor, 0);
        //initialize listView
        initializeListView();
        initializeSearchView();
        if (cursor.getCount()==0){
            emptyDatabase = true;
            Log.d(TAG, "afterInitViews() empty database");
        }
    }

    private void initializeListView(){
        placeListView.setAdapter(placeListAdapter);
        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = placeListAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    String str = ""+cursor.getLong(cursor.getColumnIndex("_id"));
                    Toast.makeText(getActivity(), cursor.getString(cursor.getColumnIndex(PlaceDbModel.COLUMN_NAME)), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeSearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setQueryString(query);
                PlaceListFragment.this.restartLoader();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                setQueryString(newText);
                PlaceListFragment.this.restartLoader();
                return true;
            }
        });
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
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
        //CREATE THE QUERY STRING
        final String selection = PlaceDbModel.COLUMN_NAME +" LIKE ? OR " + PlaceDbModel.COLUMN_DESCRIPTION + " LIKE ? ";
        if (queryString!=null && queryString.isEmpty()==false){
            String searchString = "%"+queryString+"%";
            return new CursorLoader(
                    getActivity(),
                    ContentProvider.createUri(PlaceDbModel.class, null),
                    null,
                    selection,
                    new String[]{searchString,searchString},
                    null
            );
        }
        //RETURN DEFAULT CURSOR IF EMPTY QUERY STRING
        return new CursorLoader(
                getActivity(),
                ContentProvider.createUri(PlaceDbModel.class, null),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        placeListAdapter.swapCursor(data);
        Log.d(TAG, "onLoadFinished() " + data.getCount());
        if (data.getCount()==0 ){
            //THIS MEANS THIS IS A SEARCH
            if (emptyDatabase){
                Log.d(TAG, "onLoadFinished() firstLoad place database empty");
                //showErrorMessage(getString(R.string.empty_book));
            }else{
                Log.d(TAG, "onLoadFinished() firstLoad place not found");
                //showErrorMessage(getString(R.string.book_not_found2));
            }
        }else{
            emptyDatabase=false;
            //hideErrorMessage();
            Log.d(TAG, "onLoadFinished() not empty");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        placeListAdapter.swapCursor(null);
    }
}
