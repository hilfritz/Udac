package com.hilfritz.myappportfolio.ui.music.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.BaseFragment;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.tools.StringUtil;
import com.hilfritz.myappportfolio.ui.music.topten.TopTenTracksActivity;
import com.hilfritz.spotsl.requests.SearchArtistRequest;
import com.hilfritz.spotsl.wrapper.Item;
import com.hilfritz.spotsl.wrapper.SearchWrapper;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public class SearchArtistFragment extends BaseFragment implements SearchArtistAdapter.ListItemViewHolder.ListItemClickListener {
    private static final String TAG = "SearchArtistFragment";
    @InjectView(R.id.artistRecyclerView)
    RecyclerView artistRecyclerView;

    @InjectView(R.id.artistSearchView)
    SearchView searchView;

    @InjectView(R.id.emptyTextView)
    TextView emptyTextView;

    List<Item> artistList = new ArrayList<Item>();
    SearchArtistAdapter searchArtistAdapter ;
    int activeIndex = -1;
    int previousActiveIndex = -1;

    /**
     *  When setting singleChoiceMode, ListView/recyclerview will automatically
     * give items the 'activated' state when touched.
     */
    boolean singleChoiceMode = false;
    SearchArtistFragmentCallbacks callback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_artist,container, false);
        ButterKnife.inject(this, view);
        return view;
    }



    public int getPreviousActiveIndex() {
        return previousActiveIndex;
    }

    public void setPreviousActiveIndex(int previousActiveIndex) {
        this.previousActiveIndex = previousActiveIndex;
    }

    public void setSingleChoiceMode(boolean setSingleChoiceMode) {
        this.singleChoiceMode = setSingleChoiceMode;
    }



    @Override
    public void handleClick(View view) {
        SearchArtistActivity searchArtistActivity = (SearchArtistActivity)getActivity();
        if (searchArtistActivity.isTwoPaneMode()==false) {
            Item item = (Item) view.findViewById(R.id.textView).getTag(R.string.search_activity);
            //((SearchArtistActivity)searchArtistFragment.getActivity()).shortToast("artist name:" + item.getName());
            Intent intent = new Intent(getActivity(), TopTenTracksActivity.class);
            intent.putExtra(TopTenTracksActivity.EXTRA_ARTIST_ID, item.getId());
            intent.putExtra(TopTenTracksActivity.EXTRA_ARTIST_NAME, item.getName());
            getActivity().startActivity(intent);
        }else{
            Item item = (Item) view.findViewById(R.id.textView).getTag(R.string.search_activity);
            //view.setActivated(true);
            TextView textView = (TextView)view.findViewById(R.id.textView);;
            int index = (int)textView.getTag(R.string.index);
            setActiveIndex(index);
            //REMOVE THE PREVIOUS ACTIVE INDEX
            clearActiveIndex(getPreviousActiveIndex());
            //UPDATE RECYCLERVIEW TO SHOW THE ACTIVE INDEX
            updateActiveSearchArtistIndexListItem(index, view);
            callback.onArtistItemSelected(item);
        }
    }

    /**
     *
     * @param index int the index to be cleared, IMPORTANT this index should be > 0
     */
    private void clearActiveIndex(int index){
        if (index == -1){
            Log.d(TAG, "cleaActiveIndex() no active index yet");
            return;
        }

        View view = artistRecyclerView.getChildAt(index);
        RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundResource(R.drawable.item_listview_odd_selector);
    }

    private void updateActiveSearchArtistIndexListItem(int index, View view){
        RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundResource(R.drawable.item_listview_active);
    }

    @Override
    public void initialize() {
        searchArtistAdapter = new SearchArtistAdapter(artistList, SearchArtistFragment.this);
        searchArtistAdapter.setListItemClickListener(this);
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        artistRecyclerView.setAdapter(searchArtistAdapter);;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                artistList.clear();
                searchArtistAdapter.notifyDataSetChanged();

                emptyTextView.setText("Searching " + searchView.getQuery().toString() + " please wait... ");
                //PERFORM SEARCH
                SearchArtistRequest searchArtistRequest = new SearchArtistRequest(searchView.getQuery().toString(), SearchArtistRequest.DEFAULT_LIMIT * 10, SearchArtistRequest.DEFAULT_OFFSET);
                ((BaseActivity) getActivity()).getSpiceManager().execute(searchArtistRequest, "search", DurationInMillis.ALWAYS_EXPIRED, new SearchRequestListener());
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                emptyTextView.setText(getString(R.string.press_search_to_look_for_artist, newText));
                artistRecyclerView.setVisibility(View.GONE);
                if (StringUtil.isNullOrEmptyString(newText)){
                    if (callback!=null)
                        callback.onSearchArtistListClear();
                }
                return true;
            }
        });
    }

    private class SearchRequestListener implements RequestListener<SearchWrapper> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //spiceException.
            //showArtistNotFound();
            showErrorMessage(spiceException.getLocalizedMessage());
        }

        @Override
        public void onRequestSuccess(SearchWrapper searchWrapper) {
            String queryString = searchView.getQuery().toString();
            artistList.addAll(searchWrapper.getArtists().getItems());
            if (artistList == null){
                showArtistNotFound();
                return;
            }
            if (artistList.size() == 0){
                showArtistNotFound();
                return;
            }
            //THERE SHOULD BE NO ACTIVE INDEX YET
            searchArtistAdapter.setActiveIndex(-1);
            searchArtistAdapter.notifyDataSetChanged();
            artistRecyclerView.setVisibility(View.VISIBLE);
            //emptyTextView.setText("complete with "+searchWrapper.getArtists().getItems().size());
            emptyTextView.setText("");
        }
    }

    private void showArtistNotFound(){
        String queryString = searchView.getQuery().toString();
        emptyTextView.setText(getString(R.string.no_artist_found, queryString));
        artistRecyclerView.setVisibility(View.GONE);
    }

    private void showErrorMessage(String str){
        emptyTextView.setText(str);
        emptyTextView.setVisibility(View.VISIBLE);
        artistRecyclerView.setVisibility(View.GONE);
    }
    public void setCallback(SearchArtistFragmentCallbacks callback) {
        this.callback = callback;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    /**
     * Sets {@link SearchArtistFragment#activeIndex}
     * @param activeIndex int the index of the list item that is clicked
     */
    public void setActiveIndex(int activeIndex) {
        //SET THE PREVIOUS INDEX, TO BE ABLE TO REFERENCE IT TO REMOVE ACTIVE STATE
        this.setPreviousActiveIndex(getActiveIndex());
        this.activeIndex=activeIndex;
        searchArtistAdapter.setActiveIndex(activeIndex);

    }

    /**
    * A callback interface that all activities containing this fragment must
    * implement. This mechanism allows activities to be notified of item
    * selections.
    */
    public interface SearchArtistFragmentCallbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onArtistItemSelected(Item item);
        public void onSearchArtistListClear();
    }

}
