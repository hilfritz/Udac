package com.hilfritz.myappportfolio.ui.music.topten;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilfritz.myappportfolio.AppMainApplication;
import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.BaseFragment;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.delegate.ui.music.topten.TopTenTracksEventDelegate;
import com.hilfritz.spotsl.requests.BaseRequest;
import com.hilfritz.spotsl.requests.SearchArtistTopTracksRequest;
import com.hilfritz.spotsl.wrapper.TopTracksWrapper;
import com.hilfritz.spotsl.wrapper.Track;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TopTenTracksFragment extends BaseFragment implements TopTenTracksAdapter.TopTrackListItemViewHolder.ListItemClickListener {
    private static final String TAG = "TopTenTracksFragment";

    @Bind(R.id.recyclerView)
    RecyclerView topTracksRecyclerView;

    @Bind(R.id.emptyTextView)
    TextView emptyTextView;

    String artistId;
    String artistName;

    List<Track> tracksList = new ArrayList<Track>();
    TopTenTracksAdapter topTenTracksAdapter;

    @Inject
    BaseRequest baseRequest;

    public TopTenTracksFragment() {

    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_ten_music, container, false);
        ButterKnife.bind(this, view);
        (((AppMainApplication)getActivity().getApplication()).getRestApiComponent()).inject(this);

        topTenTracksAdapter = new TopTenTracksAdapter(tracksList, TopTenTracksFragment.this);
        topTenTracksAdapter.setListItemClickListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        topTracksRecyclerView.setLayoutManager(llm);
        topTracksRecyclerView.setAdapter(topTenTracksAdapter);

        artistId = getActivity().getIntent().getStringExtra(TopTenTracksActivity.EXTRA_ARTIST_ID);
        artistName = getActivity().getIntent().getStringExtra(TopTenTracksActivity.EXTRA_ARTIST_NAME);

        if (artistId!=null && artistId.isEmpty()==false)
            populate();

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    public void populate(){
        Log.d(TAG,"populate");
        showLoadingTopTracks();
        //topTenTracksAdapter = new TopTenTracksAdapter(tracksList, TopTenTracksFragment.this);
        //topTenTracksAdapter.setListItemClickListener(this);
        //topTracksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //topTracksRecyclerView.setAdapter(topTenTracksAdapter);
        //SearchArtistTopTracksRequest searchArtistTopTracksRequest = new SearchArtistTopTracksRequest(getArtistId(),SearchArtistTopTracksRequest.DEFAULT_LIMIT, SearchArtistTopTracksRequest.DEFAULT_OFFSET);
        //((BaseActivity) getActivity()).getSpiceManager().execute(searchArtistTopTracksRequest, TAG, DurationInMillis.ALWAYS_EXPIRED, new TopTracksRequestListener());
        baseRequest.getSpotifyApi().getTopTracksObservable(getArtistId(), "PH", SearchArtistTopTracksRequest.DEFAULT_LIMIT, SearchArtistTopTracksRequest.DEFAULT_OFFSET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopTracksWrapper>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        showErrorMessage(e.getLocalizedMessage());
                    }
                    @Override
                    public void onNext(TopTracksWrapper topTracksWrapper) {
                        Log.d(TAG, "onRequestSuccess");
                        tracksList.addAll(topTracksWrapper.getTracks());
                        if (tracksList==null){
                            showEmptyTrackForArtist();
                            return;
                        }
                        if (tracksList.size()==0){
                            showEmptyTrackForArtist();
                            return;
                        }
                        tracksList.addAll(topTracksWrapper.getTracks());
                        topTenTracksAdapter.notifyDataSetChanged();
                        topTracksRecyclerView.setVisibility(View.VISIBLE);
                        emptyTextView.setText("");
                    }
                });
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public void handleClick(View view) {
        Track track = (Track) view.findViewById(R.id.relativeLayout).getTag(R.string.top_ten_tracks);
        int indexStr = (int)view.findViewById(R.id.relativeLayout).getTag(R.string.index);
        TopTenTracksEventDelegate.showMusicPlayer(indexStr,track);
    }

    @Override
    public void afterInitViews() {
    }

    public void clearTopTenTracksList(){
        tracksList.clear();
        topTenTracksAdapter.notifyDataSetChanged();
    }

    public List<Track> getTracksList() {
        return tracksList;
    }

    public void showLoadingTopTracks(){
        emptyTextView.setText(getString(R.string.loading_top_tracks, artistName));
        emptyTextView.setVisibility(View.VISIBLE);
        topTracksRecyclerView.setVisibility(View.GONE);
    }

    public void showEmptyTrackForArtist(){
        emptyTextView.setText(getString(R.string.no_top_tracks, artistName));
        emptyTextView.setVisibility(View.VISIBLE);
        topTracksRecyclerView.setVisibility(View.GONE);
    }

    public void showErrorMessage(String str){

        emptyTextView.setVisibility(View.VISIBLE);
        topTracksRecyclerView.setVisibility(View.GONE);
    }

}
