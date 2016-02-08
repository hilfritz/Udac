package com.hilfritz.myappportfolio.components;

import com.hilfritz.myappportfolio.module.AppModule;
import com.hilfritz.myappportfolio.module.RestApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hilfritz P. Camallere on 2/8/2016.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
}
