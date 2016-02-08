package com.hilfritz.myappportfolio.module;

import com.hilfritz.myappportfolio.albumapi.AlbumBaseRequest;
import com.hilfritz.spotsl.requests.BaseRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 2/6/2016.
 */
@Module
public class RestApiModule {
    @Provides @Singleton
    AlbumBaseRequest provideAlbumRestApi(){
        return new AlbumBaseRequest();
    }

    @Provides @Singleton
    BaseRequest provideSpotifyBaseRequest(){
        return new BaseRequest();
    }
}
