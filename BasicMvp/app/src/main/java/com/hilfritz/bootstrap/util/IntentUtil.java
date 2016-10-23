package com.hilfritz.bootstrap.util;

import android.app.Activity;

/**
 * Created by hilfritz on 10/23/16.
 */

public class IntentUtil {
    /**
     *
     * @param activity Activity
     * @param tag String tag
     * @return String returns the extra intent with the tag <b>tag</b>, null if not present
     */
    public static String getStringIntentExtra(Activity activity, String tag){
        String retVal = null;
        if (activity.getIntent()!=null){
            if (activity.getIntent().getStringExtra(tag)!=null){
                retVal = activity.getIntent().getStringExtra(tag);
            }
        }
        return retVal;
    }


    public static int getIntIntentExtra(Activity activity, String key, int defaultValue){
        int retVal = defaultValue;
        if (activity.getIntent()!=null){
            retVal = activity.getIntent().getIntExtra(key, defaultValue);
        }
        return retVal;
    }

}
