package com.hilfritz.bootstrap.view.detail;

import android.app.Activity;

import com.hilfritz.bootstrap.api.pojo.UserWrapper;

/**
 * Created by Hilfritz P. Camallere on 6/5/2016.
 */

public class UserDetailFragmentPresenter {
    Activity activity;
    UserDetailActivityFragment fragment;

    public UserDetailFragmentPresenter() {
    }

    public UserDetailFragmentPresenter(Activity activity, UserDetailActivityFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }

    public void populate(UserWrapper user){
        fragment.populate(user);
    }
}
