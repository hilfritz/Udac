package com.hilfritz.jokedisplayer;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class JokeActivity extends ActionBarActivity {
    public static final String TAG = "MainActivity";
    public static final String JOKE = "joke";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);


        textView = (TextView)findViewById(R.id.textView);
        //RETRIEVE THE JOKE FROM INTENT EXTRA
        Intent intent = getIntent();
        String joke = intent.getStringExtra(JokeActivity.JOKE);
        //SHOW THE JOKE ON THE FRAGMENT
        //Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
        //jokeDisplayFragment.showMessage(joke);
        textView.setText(joke);
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



}
