package com.hilfritz.myappportfolio.ui.music.player;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Window;

import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.ui.music.topten.TopTenTracksActivity;

public class MusicPlayerActivity extends BaseActivity implements MusicPlayerFragment.MediaPrepareListener{

    public static final String ARTIST_NAME = "artistName";
    public static final String ALBUM_NAME = "albumName";
    public static final String ALBUM_ARTWORK_URL = "artworkUrl";
    public static final String TRACK_NAME = "trackName";
    public static final String TRACK_DURATION = "trackDuration";
    public static final String TRACK_PREVIEW_URL = "trackPreviewUrl";
    /**
     * <ul>
     *     <li>MusicPlayerActivity.SKIP_TO_NONE - don't skip</li>
     *     <li>MusicPlayerActivity.SKIP_TO_PREVIOUS - skip to previous track</li>
     *     <li>MusicPlayerActivity.SKIP_TO_NEXT - skip to next track</li>
     * </ul>
     */
    public static final String SKIP_TO = "skipTo";
    public static final String SKIP_TO_NONE = "skipToNone";
    public static final String SKIP_TO_PREVIOUS = "skipToPrevious";
    public static final String SKIP_TO_NEXT = "skipToNext";
    public static final String INDEX = "index";
    private MusicPlayerFragment musicPlayerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_music_player);
    }

    @Override
    public void initialize() {
        showProgressInActionBar();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.musicPlayer);
        musicPlayerFragment = (MusicPlayerFragment)fragment;

        getIntent().getIntExtra("",0);
        musicPlayerFragment.initialize(
                getIntent().getStringExtra(MusicPlayerActivity.ARTIST_NAME),
                getIntent().getStringExtra(    MusicPlayerActivity.ALBUM_NAME),
                getIntent().getStringExtra(    MusicPlayerActivity.ALBUM_ARTWORK_URL),
                getIntent().getStringExtra(    MusicPlayerActivity.TRACK_NAME),
                getIntent().getStringExtra(MusicPlayerActivity.TRACK_DURATION),
                getIntent().getStringExtra(MusicPlayerActivity.TRACK_PREVIEW_URL),
                getIntent().getIntExtra(MusicPlayerActivity.INDEX,0)
        );
        musicPlayerFragment.setMediaPrepareListener(this);
    }

    public void showProgressInActionBar(){
        setProgressBarIndeterminateVisibility(true);
    }
    public void hideProgressInActionBar(){
        setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void onMediaPlayerPrepared() {
        hideProgressInActionBar();
    }

    @Override
    public void onSkipToPrevious() {
        Intent intent = new Intent();
        intent.putExtra(SKIP_TO, SKIP_TO_PREVIOUS);
        intent.putExtra(INDEX, getIntent().getIntExtra(MusicPlayerActivity.INDEX, 0));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSkipToNext() {
        Intent intent = new Intent();
        intent.putExtra(SKIP_TO, SKIP_TO_NEXT);
        intent.putExtra(INDEX, getIntent().getIntExtra(MusicPlayerActivity.INDEX,0));
        setResult(RESULT_OK, intent);
        finish();
    }
}
