package com.udacity.gradle.builditbigger;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.JokeProvider;
import com.hilfritz.jokedisplayer.JokeActivity;


public class MainActivity extends AppCompatActivity {
    ProgressDialog ringProgressDialog;
    com.udacity.gradle.builditbigger.MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        mainActivityFragment = com.udacity.gradle.builditbigger.MainActivityFragment.findOrCreateRetainFragment(fm);

        //Fragment fragment = fm.findFragmentById(R.id.fragment);
        //mainActivityFragment = (MainActivityFragment)fragment;
    }

    public com.udacity.gradle.builditbigger.MainActivityFragment getMainActivityFragment() {
        return mainActivityFragment;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    public void tellJoke(View view){
        mainActivityFragment.getNewTaskInstance(this).execute();
    }


    public void tellLocalJoke(View view){
        showProgressDialog();
        JokeProvider jokeProvider = new JokeProvider();
        startJokeDisplayerIntent(jokeProvider.getRandomTagalogJokes());
        dismissProgressDialog();
    }


    public void startJokeDisplayerIntent(String joke){
        Intent intent = new Intent(getApplicationContext(), JokeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(JokeActivity.JOKE, joke);
        startActivity(intent);
    }

    public void showProgressDialog(){
        dismissProgressDialog();
        ringProgressDialog = ProgressDialog.show(MainActivity.this, getString(R.string.please_wait), getString(R.string.loading_joke), true);
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
    }

    public void dismissProgressDialog(){
        try {
            if (ringProgressDialog != null) {
                if (ringProgressDialog.isShowing()) {
                    ringProgressDialog.dismiss();
                    ringProgressDialog = null;
                }
            }
        }catch(Exception e){
            Log.d("MainActivity", "exception could happen because of the orientation changes. please see additional stacktrace");

            e.printStackTrace();
        }finally{
            Log.d("MainActivity", "dismissing dialog finally to null");
            ringProgressDialog = null;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult()");
        if (requestCode==RESULT_OK ){
            Log.d("MainActivity", "onActivityResult() RESULTOK");
            if (data.getStringExtra("flag").equalsIgnoreCase("hideDialog")){
                dismissProgressDialog();

                Log.d("MainActivity", "onActivityResult() hide dialog");
            }
        }
    }

}
