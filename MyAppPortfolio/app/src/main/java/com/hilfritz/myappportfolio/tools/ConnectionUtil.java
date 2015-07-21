package com.hilfritz.myappportfolio.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hilfritz P. Camallere on 6/28/2015.
 */
public class ConnectionUtil {
    private static final String TAG = "ConnectionUtil";
    /**
     * Check if connected to a network
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Check if the network you are connected to has internet connection
     * https://stackoverflow.com/questions/6493517/detect-if-android-device-has-internet-connection
     * @param context
     * @return
     */

    public static boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e(TAG, "hasActiveInternetConnection() Error checking internet connection", e);
            }
        } else {
            Log.d(TAG, "hasActiveInternetConnection() No network available!");
        }
        return false;
    }


}
