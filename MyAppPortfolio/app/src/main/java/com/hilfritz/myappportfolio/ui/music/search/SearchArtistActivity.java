package com.hilfritz.myappportfolio.ui.music.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.eventbus.BusProvider;
import com.hilfritz.myappportfolio.eventbus.SearchArtistEvent;
import com.hilfritz.myappportfolio.eventbus.TopTenTracksEvent;
import com.hilfritz.myappportfolio.ui.music.MusicPlayerAppUtil;
import com.hilfritz.myappportfolio.ui.music.player.MusicPlayerDialogFragment;
import com.hilfritz.myappportfolio.ui.music.topten.TopTenTracksFragment;
import com.hilfritz.spotsl.wrapper.Image;
import com.hilfritz.spotsl.wrapper.Item;
import com.hilfritz.spotsl.wrapper.Track;
import com.squareup.otto.Subscribe;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public class SearchArtistActivity extends BaseActivity implements MusicPlayerDialogFragment.MediaPrepareListener {
    SearchArtistFragment searchArtistFragment;
    TopTenTracksFragment topTenTracksFragment;
    boolean twoPaneMode = false;
    private static final String TAG = "SearchArtistActivity";
    private MusicPlayerDialogFragment musicPlayerDialogFragment;
    public static final int SKIP_NEXT = 1;
    public static final int SKIP_PREVIOUS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }

    @Override
    public void initialize() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.searchFragment);
        searchArtistFragment = (SearchArtistFragment)fragment;

        if (findViewById(R.id.fragmentContainer)!=null){
            twoPaneMode = true;
            searchArtistFragment.setSingleChoiceMode(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    public boolean isTwoPaneMode() {
        return twoPaneMode;
    }

    @Subscribe
    public void onSearchArtistListEvent(SearchArtistEvent event){
        switch (event.getMode()) {
            case SearchArtistEvent.CLEAR:
                    if (isTwoPaneMode()) {
                        if (topTenTracksFragment != null) {    //PLACE HERE BECAUSE THE FARGMENT MAY NOT YET BE CREATED
                            topTenTracksFragment.clearTopTenTracksList();
                        } else {
                            Log.d(TAG, "onSearchArtistListClear() topTenTracksFragment is null");
                        }
                    }
                break;
            case SearchArtistEvent.SHOW_RESULT_LIST_FOR_TABLET:
                if (isTwoPaneMode()){
                    //TODO test this on tablets
                    //update the list
                    topTenTracksFragment = new TopTenTracksFragment();
                    topTenTracksFragment.setArtistId(event.getItem().getId());
                    topTenTracksFragment.setArtistName(event.getItem().getName());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, topTenTracksFragment)
                            .commit();
                }
                break;
        }
    }

    @Subscribe
    public void onTopTenTracksActivityEvent(TopTenTracksEvent event){
        switch (event.mode){
            case TopTenTracksEvent.SHOW_MUSIC_PLAYER:
                showMusicPlayerDialog(event.getSelectedTrack(), event.getSelectedIndex());
                break;
        }
    }

    protected void showMusicPlayerDialog(Track track, int index){
        String artistName = track.getArtists().get(0).getName();
        String albumName = track.getAlbum().getName();
        Image image = MusicPlayerAppUtil.getImageToDisplay(track.getAlbum().getImages());
        String artworkUrl = null;
        if (image != null)
            artworkUrl = image.getUrl();
        String trackName = track.getName();
        String trackDuration = track.getDurationMs().toString();
        String trackPreviewUrl = track.getPreviewUrl();
        musicPlayerDialogFragment = new MusicPlayerDialogFragment();
        musicPlayerDialogFragment.initialize(artistName, albumName, artworkUrl, trackName, trackDuration, trackPreviewUrl, index);
        //musicPlayerDialogFragment.setCancelable(false);
        musicPlayerDialogFragment.setMediaPrepareListener(this);
        musicPlayerDialogFragment.show(getSupportFragmentManager(), MusicPlayerDialogFragment.TAG);
    }

    @Override
    public void onMediaPlayerPrepared() {

    }

    @Override
    public void onSkipToPrevious() {
        skipTo(0);
    }

    @Override
    public void onSkipToNext() {
        skipTo(1);
    }

    /**
     *
     * @param mode int
     *             <ul>
     *             <li>0 - skip to previous</li>
     *             <li>1 - skip to next</li>
     *              </ul>
     */
    protected void skipTo(int mode){

        musicPlayerDialogFragment.dismiss();

        int index =  musicPlayerDialogFragment.getIndex();
        int trackListSize = topTenTracksFragment.getTracksList().size();
        Track track = null;
        switch (mode){
            case 0://LEFT
                if ((index-1)<0){
                    Log.d(TAG, "skipTo() no more track to skip to previous");
                    shortToast(getString(R.string.no_track_to_skip_to_previous));
                    return;
                }
                track = topTenTracksFragment.getTracksList().get(index-1);
                showMusicPlayerDialog(track, index-1);
                break;
            case 1://RIGHT
                if (trackListSize == (index +1)){
                    Log.d(TAG, "skipTo() no more track to skip to next");
                    shortToast(getString(R.string.no_track_to_skip_to_next));
                    return;
                }
                track = topTenTracksFragment.getTracksList().get(index+1);
                showMusicPlayerDialog(track, index+1);
                break;
            default:
                Log.i(TAG, "skipto() no track to skip to previous/next");
                break;
        }
    }
}
