package com.hilfritz.musicplayercountdowntimer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    TextView textView;
    private CountDownTimerWithPause countDownTimerWithPause;
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        countDownTimerWithPause = new CountDownTimerWithPause(5000,1000, 0);
        countDownTimerWithPause.setListener(new CountDownTimerWithPause.CountDownTimerWithPauseEventListener() {
            @Override
            public void onTick() {
                Log.d(TAG, "tick()" );
            }

            @Override
            public void onFinish() {
                //shortToast("finish");
            }
        });
    }

    public void startTimer(View view){
        countDownTimerWithPause.start();
    }
    public void cancelTimer(View view){
        countDownTimerWithPause.cancel();
    }
    public void pauseTimer(View view){
        countDownTimerWithPause.pause();
    }
    public void resumeTimer(View view){
        if (countDownTimerWithPause.isPaused()){
            countDownTimerWithPause.resume();
        }
    }

}
