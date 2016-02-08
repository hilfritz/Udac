package com.hilfritz.spotsl.requests;

import android.widget.Toast;

import com.hilfritz.spotsl.SpotifyApi;
import com.hilfritz.spotsl.wrapper.Artists;

import java.util.List;

import retrofit.RestAdapter;
import retrofit2.Retrofit;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 * see https://kmangutov.wordpress.com/2015/03/28/android-mvp-consuming-restful-apis/
 */
public class BaseRequest {
    static RestAdapter restAdapter;
    static SpotifyApi spotifyApi;

    public BaseRequest() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(SpotifyApi.SPOTIFY_ENDPOINT)
                .build();
        spotifyApi = getRestAdapter().create(SpotifyApi.class);
    }

    public RestAdapter getRestAdapter(){
        return restAdapter;
    }

    public SpotifyApi getSpotifyApi(){
        return spotifyApi;
    }
}
