package com.hilfritz.myappportfolio.ui.music.topten;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.eventbus.BusProvider;
import com.hilfritz.myappportfolio.eventbus.TopTenTracksEvent;
import com.hilfritz.myappportfolio.ui.music.MusicPlayerAppUtil;
import com.hilfritz.myappportfolio.ui.music.player.MusicPlayerActivity;
import com.hilfritz.myappportfolio.ui.music.player.MusicPlayerFragment;
import com.hilfritz.spotsl.wrapper.Image;
import com.hilfritz.spotsl.wrapper.Track;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;

public class TopTenTracksActivity extends BaseActivity {
    TopTenTracksFragment topTenTracksFragment;
    public static final String EXTRA_ARTIST_NAME = "ARTIST_NAME";
    public static final String EXTRA_ARTIST_ID = "ARTIST_ID";
    public static final int PLAY_MUSIC_REQUEST_CODE = 1;
    private static final String TAG = "TopTenTracksActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_top_ten_tracks);
        ButterKnife.bind(this);
    }

    @Override
    public void initialize() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.topTracksFragment);
        topTenTracksFragment = (TopTenTracksFragment) fragment;
        topTenTracksFragment.setArtistName(getIntent().getStringExtra(EXTRA_ARTIST_NAME));
        topTenTracksFragment.setArtistId(getIntent().getStringExtra(EXTRA_ARTIST_ID));
        //topTenTracksFragment.populate();
        Log.d(TAG, "artistName=" + getIntent().getStringExtra(EXTRA_ARTIST_NAME) + " id=" + getIntent().getStringExtra(EXTRA_ARTIST_ID));
    }

    @Subscribe
    public void onTopTenTracksActivityEvent(TopTenTracksEvent event){
        switch (event.mode){
            case TopTenTracksEvent.SHOW_MUSIC_PLAYER:
                startMusicPlayerActivity(event.getSelectedTrack(), event.getSelectedIndex());
                break;
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

    private void startMusicPlayerActivity(Track track, int index){
        String artistName = track.getArtists().get(0).getName();
        String albumName = track.getAlbum().getName();
        Image image = MusicPlayerAppUtil.getImageToDisplay(track.getAlbum().getImages());
        String artworkUrl = null;
        if (image != null)
            artworkUrl = image.getUrl();

        String trackName = track.getName();
        String trackDuration = track.getDurationMs().toString();
        String trackPreviewUrl = track.getPreviewUrl();


        Intent intent = new Intent(this, MusicPlayerActivity.class);
        intent.putExtra(MusicPlayerActivity.INDEX, index);
        intent.putExtra(MusicPlayerActivity.ARTIST_NAME, artistName);
        intent.putExtra(MusicPlayerActivity.ALBUM_NAME, albumName);
        intent.putExtra(MusicPlayerActivity.ALBUM_ARTWORK_URL, artworkUrl);
        intent.putExtra(MusicPlayerActivity.TRACK_NAME, trackName);
        intent.putExtra(MusicPlayerActivity.TRACK_DURATION, trackDuration);
        intent.putExtra(MusicPlayerActivity.TRACK_PREVIEW_URL, trackPreviewUrl);
        startActivityForResult(intent, TopTenTracksActivity.PLAY_MUSIC_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode == TopTenTracksActivity.PLAY_MUSIC_REQUEST_CODE) {
                if (data.getStringExtra(MusicPlayerActivity.SKIP_TO).equalsIgnoreCase(MusicPlayerActivity.SKIP_TO_NEXT)) {
                    skipTo(data,1);
                } else if (data.getStringExtra(MusicPlayerActivity.SKIP_TO).equalsIgnoreCase(MusicPlayerActivity.SKIP_TO_PREVIOUS)) {
                    skipTo(data,0);
                }
            }else{
                Log.d(TAG, "onActivityResult() SKIP_TO not okay");
            }
        }else{
            Log.d(TAG, "onActivityResult() result not ok");
        }
    }

    /**
     *
     * @param data Intent
     * @param mode int
     *             <ul>
     *             <li>0 - skip to previous</li>
     *             <li>1 - skip to next</li>
     *             </ul>
     */
    protected void skipTo(Intent data, int mode){
        int index =  data.getIntExtra(MusicPlayerActivity.INDEX,0);
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
                startMusicPlayerActivity(track, index-1);
                break;
            case 1://RIGHT
                if (trackListSize == (index +1)){
                    Log.d(TAG, "skipTo() no more track to skip to next");
                    shortToast(getString(R.string.no_track_to_skip_to_next));
                    return;
                }

                track = topTenTracksFragment.getTracksList().get(index+1);
                startMusicPlayerActivity(track, index+1);
                break;
            default:
                Log.i(TAG, "skipto() no track to skip to previous/next");
                break;
        }

    }
}
