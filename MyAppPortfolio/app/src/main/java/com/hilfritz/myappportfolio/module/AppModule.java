package com.hilfritz.myappportfolio.module;

import android.content.Context;

import com.hilfritz.myappportfolio.AppMainApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 2/8/2016.
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    Context provideContext(Context context){
        return context;
    }

    @Provides @Singleton
    AppMainApplication provideAppMainApplication(Context context){
        return (AppMainApplication)context.getApplicationContext();
    }

}
