package com.hilfritz.bootstrap.view;

import android.util.Log;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class BasePresenter {
    private static final String TAG = "BasePresenter";
    /**
     * True - if the activity is newly created
     * False - if the activity is just rotated
     */
    boolean initialLoad = true;

    public boolean isInitialLoad() {
        Log.d(TAG, "isInitialLoad: "+((initialLoad)?"true":"false"));
        return initialLoad;
    }

    public void setInitialLoad(boolean initialLoad) {
        Log.d(TAG, "setInitialLoad: set to:"+((initialLoad)?"true":"false"));
        this.initialLoad = initialLoad;
    }

}
