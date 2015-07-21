package com.hilfritz.spotsl.requests;

import android.widget.Toast;

import com.hilfritz.spotsl.SpotifyApi;
import com.hilfritz.spotsl.wrapper.Artists;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by Hilfritz P. Camallere on 6/14/2015.
 */
public abstract class BaseRequest {
    RestAdapter restAdapter;
    public static final String SPOTIFY_ENDPOINT = "https://api.spotify.com/v1";
    SpotifyApi spotifyApi;

    public BaseRequest() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(SPOTIFY_ENDPOINT)
                .build();

        spotifyApi = restAdapter.create(SpotifyApi.class);
    }

    public SpotifyApi getSpotifyApi(){
        return this.spotifyApi;
    }

    public RestAdapter getRestAdapter() {
        return restAdapter;
    }
}
