package com.hilfritz.musicplayercountdowntimer;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A countdown timer,
 * @author  by hilfritz.p.camallere on 6/19/2015.
 */
public class CountDownTimerWithPause {
    public static final int SINGLE_MODE = 0;
    public static final int DUAL_MODE = 0;
    private static final String TAG = "CountDownTimerWithPause";
    int mode = SINGLE_MODE;
    CountDownTimer countDownTimer;
    /**
     * The duration of the timer.
     * <br> In milliseconds.
     */
    long countDownDurationMs = 0;
    /**
     * The interval of the timer,
     * <br> In milliseconds.
     */
    long countDownIntervalMs;
    int consumedInSeconds = 0;
    protected boolean isPaused = false;
    protected boolean isFinished = false;

    DateTimeFormatter minSecFormat = DateTimeFormat.forPattern("mm:ss");
    DateTimeFormatter hrMinSecFormat = DateTimeFormat.forPattern("HH:mm:ss");
    CountDownTimerWithPauseEventListener listener;



    /**
     *
     * @param countDownDurationMs long duration of the timer
     * @param countDownIntervalMs long intervals of the timer
     * @param mode int either {@link CountDownTimerWithPause#SINGLE_MODE}, {@link CountDownTimerWithPause#DUAL_MODE}
     */
    public CountDownTimerWithPause(long countDownDurationMs, long countDownIntervalMs, int mode) {
        this.countDownDurationMs = countDownDurationMs;
        this.countDownIntervalMs = countDownIntervalMs;
        this.mode = mode;
        isPaused = false;
    }
    /**
    * @param countDownDurationMs long duration of the timer
    * @param countDownIntervalMs long intervals of the timer
    */
    public CountDownTimerWithPause(long countDownDurationMs, long countDownIntervalMs) {
        this.countDownDurationMs = countDownDurationMs;
        this.countDownIntervalMs = countDownIntervalMs;
        isPaused = false;
    }

    protected void createAndStartTimer(){
        isPaused = false;
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(countDownDurationMs, countDownIntervalMs) {
            @Override
            public void onTick(long l) {
                countDownDurationMs = countDownDurationMs - countDownIntervalMs;
                consumedInSeconds = consumedInSeconds + 1;
                listener.onTick();
            }
            @Override
            public void onFinish() {
                listener.onFinish();
            }
        };
        countDownTimer.start();
    }

    public long getCountDownDurationMs() {
        return countDownDurationMs;
    }
    public int getConsumedInSeconds() {
        return consumedInSeconds;
    }
    public void setListener(CountDownTimerWithPauseEventListener listener) {
        this.listener = listener;
    }

    public void cancel(){

        consumedInSeconds = 0;
        if (countDownTimer!=null)
            countDownTimer.cancel();
    }

    public void pause(){
        isPaused = true;
        countDownTimer.cancel();
    }
    public boolean isPaused() {
        return isPaused;
    }

    public void resume(){
        if (isFinished){
            Log.d(TAG, "resume() cannot resume, already finished.");
        }
        if (isPaused)
            createAndStartTimer();
    }
    public void start(){
        createAndStartTimer();
    }
    public void seekTo(long seekMs){
        countDownDurationMs = seekMs;
        createAndStartTimer();
    }
    public interface CountDownTimerWithPauseEventListener{
        void onTick();
        void onFinish();
    }
}
