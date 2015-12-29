package com.hilfritz.myappportfolio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.hilfritz.myappportfolio.ui.music.search.SearchArtistActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{

   @Bind(R.id.spotifyBtn) Button spotifyBtn;
   @Bind(R.id.scoresBtn) Button scoresBtn;
   @Bind(R.id.libraryBtn) Button libraryBtn;
   @Bind(R.id.buildBtn) Button buildBtn;
   @Bind(R.id.xyzBtn) Button xyzBtn;
   @Bind(R.id.capstoneBtn) Button capstoneBtn;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
