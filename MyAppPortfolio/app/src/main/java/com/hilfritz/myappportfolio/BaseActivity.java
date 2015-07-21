package com.hilfritz.myappportfolio;

import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.hilfritz.spotsl.network.SpotifySpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by Hilfritz P. Camallere on 6/6/2015.
 */
public abstract class BaseActivity extends ActionBarActivity {
    private SpiceManager spiceManager = new SpiceManager(SpotifySpiceService.class);
    Toast toast;
    public void shortToast(String str){
        if (toast!=null){
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT);
        toast.show();
    }
    public void longToast(String str){
        if (toast!=null){
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), str,Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onStart() {
        spiceManager.start(getApplicationContext());
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        spiceManager.shouldStop();
    }



    public SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
