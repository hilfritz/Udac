package com.hilfritz.favoriteplacesmodule;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Hilfritz P. Camallere on 1/2/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
