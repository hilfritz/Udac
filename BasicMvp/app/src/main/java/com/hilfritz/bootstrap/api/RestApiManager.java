package com.hilfritz.bootstrap.api;

import com.hilfritz.bootstrap.api.pojo.UserWrapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 * see https://guides.codepath.com/android/Consuming-APIs-with-Retrofit
 */

public class RestApiManager {
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    public static final String USERS_URL = BASE_URL+"/users";
    RestApiInterface api ;
    Retrofit retrofit;

    public RestApiManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //very important for RXJAVA
                .build();
        api = retrofit.create(RestApiInterface.class);

    }

    public Observable<List<UserWrapper>> getUsersSubscribable(){
        return api.getUsersObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .delay(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                ;
    }
}
