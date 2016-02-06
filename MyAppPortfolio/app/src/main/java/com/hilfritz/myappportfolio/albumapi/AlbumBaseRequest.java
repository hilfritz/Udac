package com.hilfritz.myappportfolio.albumapi;

import retrofit.RestAdapter;

/**
 * Created by Hilfritz P. Camallere on 2/3/2016.
 */
public class AlbumBaseRequest {
    RestAdapter restAdapter;
    AlbumListApi albumListApi;

    public AlbumBaseRequest() {
         restAdapter = new RestAdapter.Builder()
                .setEndpoint(AlbumListApi.URL)
                .build();
    }
    public RestAdapter getRestAdapter(){
        return restAdapter;
    }
    public AlbumListApi getApi(){
        if (albumListApi==null)
            albumListApi = getRestAdapter().create(AlbumListApi.class);
        return albumListApi;
    }
}
