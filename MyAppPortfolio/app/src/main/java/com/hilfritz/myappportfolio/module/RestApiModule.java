package com.hilfritz.myappportfolio.module;

import com.hilfritz.myappportfolio.albumapi.AlbumBaseRequest;

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
}
