package com.hilfritz.myappportfolio.components;

import com.hilfritz.myappportfolio.albumapi.AlbumBaseRequest;
import com.hilfritz.myappportfolio.module.RestApiModule;
import com.hilfritz.myappportfolio.ui.album.list.AlbumActivity;
import com.hilfritz.myappportfolio.ui.album.photolist.AlbumPhotoActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hilfritz P. Camallere on 2/6/2016.
 */
@Singleton
@Component(modules = {RestApiModule.class})
public interface RestApiComponent {

    void inject(AlbumActivity albumActivity); //this tells that all @Provides methods in the 'modules' will be available for injecton
    void inject(AlbumPhotoActivity albumPhotoActivity);
}
