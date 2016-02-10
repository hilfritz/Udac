package com.hilfritz.myappportfolio.albumapi;

import com.hilfritz.myappportfolio.albumapi.pojo.Album;
import com.hilfritz.myappportfolio.albumapi.pojo.Users;

import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hilfritz P. Camallere on 2/3/2016.
 */
public class AlbumApiManager {
    RestAdapter restAdapter;
    AlbumListApi albumListApi;

    public AlbumApiManager() {
         restAdapter = new RestAdapter.Builder()
                .setEndpoint(AlbumListApi.URL)
                .build();
        albumListApi = getRestAdapter().create(AlbumListApi.class);
    }
    public RestAdapter getRestAdapter(){
        return restAdapter;
    }
    public AlbumListApi getApi(){
        return albumListApi;
    }

    public Observable<List<Album>> getAlbumsObservable(){
        return getApi().getAlbumsO()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Users>> getUsersObservable(){
        return getApi().getallUserO()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
