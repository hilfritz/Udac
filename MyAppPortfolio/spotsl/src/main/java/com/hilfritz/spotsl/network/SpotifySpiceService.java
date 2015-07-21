package com.hilfritz.spotsl.network;

import com.hilfritz.spotsl.SpotifyApi;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public class SpotifySpiceService extends RetrofitGsonSpiceService {
    private final static String BASE_URL = "https://api.spotify.com/v1";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(SpotifyApi.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
