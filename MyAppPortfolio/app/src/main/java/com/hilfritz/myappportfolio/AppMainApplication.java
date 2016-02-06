package com.hilfritz.myappportfolio;

import android.app.Application;

import com.hilfritz.myappportfolio.components.DaggerRestApiComponent;
import com.hilfritz.myappportfolio.components.RestApiComponent;
import com.hilfritz.myappportfolio.module.RestApiModule;

/**
 * Created by Hilfritz P. Camallere on 2/6/2016.
 */
public class AppMainApplication extends Application {
    RestApiComponent restApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDagger();
    }

    private void initializeDagger(){
        restApiComponent = DaggerRestApiComponent.builder()
                .restApiModule(new RestApiModule())
                .build();
    }

    public RestApiComponent getRestApiComponent() {
        return restApiComponent;
    }
}
