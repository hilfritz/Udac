package com.hilfritz.bootstrap.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hilfritz.bootstrap.application.MyApplication;

/**
 * Created by Hilfritz P. Camallere on 8/20/2016.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication)getApplication()).getAppComponent().inject(this);
    }
    public void checkIfNewActivity(Bundle savedInstanceState, BasePresenter presenter) {
        if (savedInstanceState==null){
            presenter.setInitialLoad(true);
        }else {
            presenter.setInitialLoad(false);
        }
    }
}
