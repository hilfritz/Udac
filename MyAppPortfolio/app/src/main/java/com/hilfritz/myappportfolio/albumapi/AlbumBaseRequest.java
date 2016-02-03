package com.hilfritz.myappportfolio.albumapi;

import retrofit.RestAdapter;

/**
 * Created by Hilfritz P. Camallere on 2/3/2016.
 */
public class AlbumBaseRequest {
    static RestAdapter restAdapter;
    static AlbumListApi albumListApi;

    public static RestAdapter getRestAdapter(){
        if (restAdapter==null){
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(AlbumListApi.URL)
                    .build();
        }
        return restAdapter;
    }


    public static AlbumListApi getApi(){
        if (albumListApi==null)
            albumListApi = getRestAdapter().create(AlbumListApi.class);
        return albumListApi;
    }

}
