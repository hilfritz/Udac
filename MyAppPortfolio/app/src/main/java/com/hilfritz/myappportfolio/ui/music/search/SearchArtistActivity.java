package com.hilfritz.myappportfolio.ui.music.search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.ui.music.MusicPlayerAppUtil;
import com.hilfritz.myappportfolio.ui.music.player.MusicPlayerDialogFragment;
import com.hilfritz.myappportfolio.ui.music.topten.TopTenTracksFragment;
import com.hilfritz.spotsl.wrapper.Image;
import com.hilfritz.spotsl.wrapper.Item;
import com.hilfritz.spotsl.wrapper.Track;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public class SearchArtistActivity extends BaseActivity implements SearchArtistFragment.SearchArtistFragmentCallbacks, TopTenTracksFragment.OnTrackItemClickListener, MusicPlayerDialogFragment.MediaPrepareListener {
    SearchArtistFragment searchArtistFragment;
    TopTenTracksFragment topTenTracksFragment;
    boolean twoPaneMode = false;
    private static final String TAG = "SearchArtistActivity";
    private MusicPlayerDialogFragment musicPlayerDialogFragment;

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
            searchArtistFragment.setCallback(this);
        }
    }

    public boolean isTwoPaneMode() {
        return twoPaneMode;
    }

    @Override
    public void onArtistItemSelected(Item item) {
        if (isTwoPaneMode()){
            //update the list
            topTenTracksFragment = new TopTenTracksFragment();
            topTenTracksFragment.setArtistId(item.getId());
            topTenTracksFragment.setArtistName(item.getName());
            topTenTracksFragment.setOnTrackItemClickListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, topTenTracksFragment)
                    .commit();
        }
    }

    @Override
    public void onSearchArtistListClear() {
        if (isTwoPaneMode()){
            if (topTenTracksFragment!=null){    //PLACE HERE BECAUSE THE FARGMENT MAY NOT YET BE CREATED
                topTenTracksFragment.clearTopTenTracksList();
            }else{
                Log.d(TAG, "onSearchArtistListClear() topTenTracksFragment is null");
            }
        }

    }

    @Override
    public void showMusicPlayer(View view) {
        Track track = (Track) view.findViewById(R.id.relativeLayout).getTag(R.string.top_ten_tracks);
        int index = (int)view.findViewById(R.id.relativeLayout).getTag(R.string.index);
        showMusicPlayerDialog(track, index);
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
