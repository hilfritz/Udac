package com.hilfritz.myappportfolio;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.hilfritz.musicplayercountdowntimer.CountDownTimerWithPause;
import com.hilfritz.myappportfolio.ui.music.search.SearchArtistActivity;
import com.hilfritz.spotsl.requests.SearchArtistRequest;
import com.hilfritz.spotsl.requests.SearchArtistTopTracksRequest;
import com.hilfritz.spotsl.wrapper.SearchWrapper;
import com.hilfritz.spotsl.wrapper.TopTracksWrapper;
import com.hilfritz.spotsl.wrapper.Track;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{

   @InjectView(R.id.spotifyBtn) Button spotifyBtn;
   @InjectView(R.id.scoresBtn) Button scoresBtn;
   @InjectView(R.id.libraryBtn) Button libraryBtn;
   @InjectView(R.id.buildBtn) Button buildBtn;
   @InjectView(R.id.xyzBtn) Button xyzBtn;
   @InjectView(R.id.capstoneBtn) Button capstoneBtn;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({
            R.id.spotifyBtn,
            R.id.scoresBtn,
            R.id.libraryBtn,
            R.id.buildBtn,
            R.id.xyzBtn,
            R.id.capstoneBtn
            })
    public void portfolioAppsClick(Button button){
        if (button.getId() == R.id.spotifyBtn){
            Intent intent = new Intent(MainActivity.this, SearchArtistActivity.class);
            startActivity(intent);
            return;
        }
        shortToast(getString(R.string.this_button_will_lauch,button.getText().toString()));

    }

}
