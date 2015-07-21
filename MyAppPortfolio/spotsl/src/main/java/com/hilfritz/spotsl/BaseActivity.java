package com.hilfritz.spotsl;

import android.support.v7.app.ActionBarActivity;

import com.hilfritz.spotsl.network.SpotifySpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by Hilfritz P. Camallere on 6/13/2015.
 */
public class BaseActivity extends ActionBarActivity {
    private SpiceManager spiceManager = new SpiceManager(SpotifySpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }


    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
