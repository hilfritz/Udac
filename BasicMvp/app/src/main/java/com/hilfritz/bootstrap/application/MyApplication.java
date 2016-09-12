package com.hilfritz.bootstrap.application;

import android.app.Application;

import com.hilfritz.bootstrap.dagger2.component.AppComponent;
import com.hilfritz.bootstrap.dagger2.component.DaggerAppComponent;
import com.hilfritz.bootstrap.dagger2.module.SessionModule;
import com.hilfritz.bootstrap.dagger2.module.UtilityModule;
import com.hilfritz.bootstrap.dagger2.module.PresenterModule;
import com.hilfritz.bootstrap.dagger2.module.RestApiModule;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */

public class MyApplication extends Application {
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDagger();
    }

    private void initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .restApiModule(new RestApiModule())
                .presenterModule(new PresenterModule(this))
                .sessionModule(new SessionModule(this))
                .utilityModule(new UtilityModule())
                .build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


}
