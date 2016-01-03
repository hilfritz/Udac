package com.hilfritz.myappportfolio.ui.music.player;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hilfritz.musicplayercountdowntimer.CountDownTimerWithPause;
import com.hilfritz.musicplayercountdowntimer.TimeFormatUtil;
import com.hilfritz.myappportfolio.BaseActivity;
import com.hilfritz.myappportfolio.BaseFragment;
import com.hilfritz.myappportfolio.R;
import com.hilfritz.myappportfolio.tools.NumberUtil;
import com.hilfritz.myappportfolio.tools.StringUtil;
import com.hilfritz.myappportfolio.ui.music.MusicPlayerAppUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Hilfritz P. Camallere on 6/17/2015.
 * for timer
 * http://example.javamonday.com/Open-Source/Android/Timer/multitimer-android/com/cycleindex/multitimer/CountDownTimerWithPause.java.htm
 * http://stackoverflow.com/questions/8306374/android-how-to-pause-and-resume-a-count-down-timer
 * http://stackoverflow.com/questions/15443219/how-to-pause-and-resume-a-timertask-timer
 * http://stackoverflow.com/questions/8351792/android-resume-on-count-down-timer
 * http://stackoverflow.com/questions/2098642/pausing-stopping-and-starting-resuming-java-timertask-continuously/2098678#2098678
 * https://c05mic.wordpress.com/2013/05/23/timer-with-pause-and-resume-support-androidjava/
 *
 */
public class MusicPlayerFragment extends BaseFragment implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener{


    @Bind(R.id.emptyTextView) TextView emptyTextView;
    @Bind((R.id.relativeLayout)) RelativeLayout relativeLayout;
    @Bind((R.id.relativeLayout2)) RelativeLayout seekTimesLayout;
    @Bind((R.id.relativeLayout3)) RelativeLayout buttonsLayout;
    @Bind((R.id.artistNameTextView)) TextView artistNameTextView;
    @Bind((R.id.albumNameTextView)) TextView albumNameTextView;
    @Bind((R.id.artworkImageView)) ImageView artworkImageView;
    @Bind((R.id.trackNameTextView)) TextView trackNameTextView;
    @Bind((R.id.seekBar)) SeekBar seekbar;
    @Bind(R.id.leftTimeTextView) TextView leftTimeTextView;
    @Bind(R.id.rightTimeTextView) TextView rightTimeTextView;
    @Bind(R.id.loadingTextView) TextView loadingTextView;
    @Bind(R.id.seekLeftButton) ImageButton seekLeftButton;
    @Bind(R.id.seekRightButton) ImageButton seekRightButton;
    @Bind(R.id.playPauseButton) ImageButton playPauseButton;


    public static final int SPOTIFY_PREVIEW_DURATION = 30000; //30 seconds
    public static final String TAG = "MusicPlayerDFragment";
    public String    artistName;
    public String        albumName;
    public String    artworkUrl;
    public String        trackName;
    public String    trackDuration;
    public String       trackPreviewUrl;
    public int index;
    boolean firstLoad = true;

    boolean isPlayerPrepared = false;
    MediaPlayer mediaPlayer;
    CountDownTimerWithPause countDownTimerWithPause;
    long currentPositionInMs = 0;
    MediaPrepareListener mediaPrepareListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void initialize(String artistName, String albumName, String artworkUrl, String trackName, String trackDuration, String trackPreviewUrl, int index) {
        this.artistName = artistName;
        this.albumName = albumName;
        this.artworkUrl = artworkUrl;
        this.trackName = trackName;
        this.trackDuration = trackDuration;
        this.trackPreviewUrl = trackPreviewUrl;
        this.index = index;
    }
    public void populate(){
        //SHOW LOADING

        //DISABLE THE BUTTONS
        seekLeftButton.setEnabled(false);
        seekRightButton.setEnabled(false);
        playPauseButton.setEnabled(false);
        seekbar.setEnabled(false);
        //SET SOME TEXTVIEWS
        artistNameTextView.setText(artistName);
        albumNameTextView.setText(getString(R.string.album_name_format,albumName));
        MusicPlayerAppUtil.loadImage(getActivity(), artworkImageView, artworkUrl, R.drawable.music_album);
        trackNameTextView.setText(trackName);
        leftTimeTextView.setText(getString(R.string.empty_min_sec));
        trackDuration = String.valueOf(SPOTIFY_PREVIEW_DURATION);
        rightTimeTextView.setText(
                TimeFormatUtil.getFormattedDateForDisplay(
                        Long.valueOf(SPOTIFY_PREVIEW_DURATION),
                        TimeFormatUtil.MIN_SEC_FORMAT
                )
        );
        initAndPlayMusicPlayer();
        initializeSeekBar();
    }

    /**
     *
     * @param mediaPrepareListener MediaPrepareListener
     */
    public void setMediaPrepareListener(MediaPrepareListener mediaPrepareListener) {
        this.mediaPrepareListener = mediaPrepareListener;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void afterInitViews() {
        populate();
    }

    private void initializeSeekBar(){
        if (StringUtil.isNullOrEmptyString(trackPreviewUrl)==false) {
            seekbar.setMax(0);
            seekbar.setMax(TimeFormatUtil.getSecondsFromMilliseconds(Integer.valueOf(SPOTIFY_PREVIEW_DURATION)));
            seekbar.setProgress(0);
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressChanged = 0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    progressChanged = i;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //1. PAUSE
                    //2. GET THE CURRENT VALUE
                    //3. CONVERT VALUE TO MILLISECONDS
                    //4. SEEKTO
                    togglePausePlay();
                    int seekTo = TimeFormatUtil.getMilliSecondsFromSeconds(progressChanged);
                    mediaPlayer.seekTo(seekTo);
                    currentPositionInMs = seekTo;

                }
            });
            countDownTimerWithPause = new CountDownTimerWithPause(Long.valueOf(SPOTIFY_PREVIEW_DURATION), 1000, 0);
            countDownTimerWithPause.setListener(new CountDownTimerWithPause.CountDownTimerWithPauseEventListener() {
                @Override
                public void onTick() {
                    String remainingTime = TimeFormatUtil.getFormattedDateForDisplay(
                            countDownTimerWithPause.getCountDownDurationMs(),
                            TimeFormatUtil.MIN_SEC_FORMAT
                    );
                    long consumedInMilliseconds = Long.valueOf(SPOTIFY_PREVIEW_DURATION) - countDownTimerWithPause.getCountDownDurationMs();
                    String consumedTime = getString(R.string.empty_min_sec);
                    if (consumedInMilliseconds > 0) {
                        consumedTime = TimeFormatUtil.getFormattedDateForDisplay(
                                consumedInMilliseconds,
                                TimeFormatUtil.MIN_SEC_FORMAT
                        );
                    }
                    seekbar.setMax(0);
                    seekbar.setMax(
                            TimeFormatUtil.getSecondsFromMilliseconds(
                                    Integer.valueOf(SPOTIFY_PREVIEW_DURATION))
                    );
                    seekbar.setProgress(
                            TimeFormatUtil.getSecondsFromMilliseconds(
                                    NumberUtil.safeConvertLongToInt(consumedInMilliseconds))
                    );
                    Log.d(TAG, "progress:" + TimeFormatUtil.getSecondsFromMilliseconds(NumberUtil.safeConvertLongToInt(consumedInMilliseconds)));
                    rightTimeTextView.setText(remainingTime);
                    leftTimeTextView.setText(consumedTime);
                }

                @Override
                public void onFinish() {
                    //leftTimeTextView.setText();
                    long totalDurationMs = Long.valueOf(trackDuration);
                    leftTimeTextView.setText(
                            TimeFormatUtil.getFormattedDateForDisplay(
                                    totalDurationMs,
                                    TimeFormatUtil.MIN_SEC_FORMAT
                            )
                    );
                    rightTimeTextView.setText("00:00");
                    playPauseButton.setBackgroundResource(R.drawable.button_play);
                }
            });
        }
    }

    public void showPlayButton(){
        playPauseButton.setBackgroundResource(android.R.drawable.ic_media_play);
    }
    public void showPauseButton(){
        playPauseButton.setBackgroundResource(android.R.drawable.ic_media_pause);
    }

    private void showErrorMessage(){
        emptyTextView.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);
    }
    private void showMusicPlayer(){
        emptyTextView.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
    }





    /**
     * @see http://developer.android.com/reference/android/media/MediaPlayer.html
     */
    private void initAndPlayMusicPlayer(){
        if (StringUtil.isNullOrEmptyString(trackPreviewUrl)==false) {
            isPlayerPrepared = false;
            //String url = "https://p.scdn.co/mp3-preview/fbaf230cc8b34eaf186acbbe604256de7d9e61d4"; // your URL here

            //String url = "http://download.wavetlan.com/SVV/Media/HTTP/MP3/Nero_SmartTrax/NeroSmartTrax_test1_MPEG2_Stereo_CBR_48kbps_22050Hz.mp3";
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(trackPreviewUrl);
                mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.setOnErrorListener(this);
                mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        startPlayer();
                    }
                });
                mediaPlayer.setScreenOnWhilePlaying(true);
                //mediaPlayer.setOnSeekCompleteListener(this);
                //mediaPlayer.setOnBufferingUpdateListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            ((BaseActivity) getActivity()).longToast(getString(R.string.no_preview_url, trackName));
        }
    }

    private void togglePausePlay(){
        if (mediaPlayer!=null && isPlayerPrepared==true){
            if (mediaPlayer.isPlaying()){
                //PAUSE PLAYER
                mediaPlayer.pause();
                playPauseButton.setBackgroundResource(R.drawable.button_play);
                //TODO:pause timer
                countDownTimerWithPause.pause();
            }else{
                //PLAY
                mediaPlayer.start();
                //countDownTimer.start();
                countDownTimerWithPause.resume();
                playPauseButton.setBackgroundResource(R.drawable.button_pause);
            }
        }else{
            Log.d(TAG, "togglePausePlay() player is null");
        }
    }
    protected void pausePlayer(){
        if (mediaPlayer!=null && isPlayerPrepared==true){

            if (mediaPlayer.isPlaying()){
                //PAUSE PLAYER
                //TODO: SET THE UI TO PAUSED
                mediaPlayer.pause();

            }
        }else{
            Log.d(TAG, "pausePlayer() player is null");
        }
    }

    protected Drawable getDrawableSafely(int id){
        Drawable mDrawable;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            mDrawable = getActivity().getResources().getDrawable(id, getActivity().getTheme());
        } else {
            mDrawable = getActivity().getResources().getDrawable(id);
        }
        return mDrawable;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer!=null && isPlayerPrepared==true) {
            if (mediaPlayer.isPlaying()) {
                //PAUSE PLAYER
                mediaPlayer.pause();
                playPauseButton.setBackgroundResource(R.drawable.button_play);
                countDownTimerWithPause.pause();
            }
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        if (countDownTimerWithPause!=null)
            countDownTimerWithPause.cancel();
        countDownTimerWithPause = null;
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        Log.d(TAG, "OnError - Error code: "+what+" Extra code: "+extra);

        switch(what){
            case -1004:
                Log.d(TAG, "onError() MEDIA_ERROR_IO");
                break;
            case -1007:
                Log.d(TAG, "onError()MEDIA_ERROR_MALFORMED");
                break;
            case 200:
                Log.d(TAG, "onError()MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                break;
            case 100:
                Log.d(TAG, "onError() MEDIA_ERROR_SERVER_DIED");
                break;
            case -110:
                Log.d(TAG, "onError() MEDIA_ERROR_TIMED_OUT");
                break;
            case 1:
                Log.d(TAG, "onError() MEDIA_ERROR_UNKNOWN");
                break;
            case -1010:
                Log.d(TAG, "onError() MEDIA_ERROR_UNSUPPORTED");
                break;

        }

        switch(extra){
            case 800:
                Log.d(TAG, "onError() MEDIA_INFO_BAD_INTERLEAVING");
                break;
            case 702:
                Log.d(TAG, "onError() MEDIA_INFO_BUFFERING_END");
                break;
            case 701:
                Log.d(TAG, "onError() MEDIA_INFO_METADATA_UPDATE");
                break;
            case 802:
                Log.d(TAG, "onError() MEDIA_INFO_METADATA_UPDATE");
                break;
            case 801:
                Log.d(TAG, "onError() MEDIA_INFO_NOT_SEEKABLE");
                break;
            case 1:
                Log.d(TAG, "onError() MEDIA_INFO_UNKNOWN");
                break;
            case 3:
                Log.d(TAG, "onError() MEDIA_INFO_VIDEO_RENDERING_START");
                break;
            case 700 :
                Log.d(TAG, "onError() MEDIA_INFO_VIDEO_TRACK_LAGGING");
                break;

        }

        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //HIDE LOADING
        loadingTextView.setVisibility(View.GONE);
        //ENABLE BUTTONS
        seekLeftButton.setEnabled(true);
        seekRightButton.setEnabled(true);
        playPauseButton.setEnabled(true);
        seekbar.setEnabled(true);
        //HIDE ACTIONBAR LOADING
        if (mediaPrepareListener!=null)
            mediaPrepareListener.onMediaPlayerPrepared();
        startPlayer();
    }

    protected void startPlayer(){
        isPlayerPrepared = true;
        mediaPlayer.start();
        countDownTimerWithPause.seekTo(
                SPOTIFY_PREVIEW_DURATION -
                        currentPositionInMs);
        playPauseButton.setBackgroundResource(R.drawable.button_pause);
    }

    @OnClick(R.id.seekLeftButton)
    public void seekLeftClick(){
        Log.i(TAG, "seekLeftClick()");
        mediaPrepareListener.onSkipToPrevious();
    }
    @OnClick(R.id.seekRightButton)
    public void seekRightClick(){
        Log.i(TAG, "seekRightClick()");
        mediaPrepareListener.onSkipToNext();
    }
    @OnClick(R.id.playPauseButton)
    public void setPlayPauseClick(){
        Log.i(TAG, "setPlayPauseClick()");
        togglePausePlay();
    }



    public interface MediaPrepareListener{
        public void onMediaPlayerPrepared();
        public void onSkipToPrevious();
        public void onSkipToNext();
    }
}
