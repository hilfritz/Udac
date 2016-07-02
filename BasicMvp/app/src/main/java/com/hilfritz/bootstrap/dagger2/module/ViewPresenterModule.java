package com.hilfritz.bootstrap.dagger2.module;

import com.hilfritz.bootstrap.application.MyApplication;
import com.hilfritz.bootstrap.view.detail.UserDetailFragmentPresenter;
import com.hilfritz.bootstrap.view.main.userlist.UserListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 6/28/2016.
 */
@Module
public class ViewPresenterModule {


    private final MyApplication myApplication;

    public ViewPresenterModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    //FRAGMENTS HERE
    @Provides
    @Singleton
    UserDetailFragmentPresenter provideUserDetailPresenter(){
        return new UserDetailFragmentPresenter();
    }
    @Provides
    @Singleton
    UserListPresenter provideUserListPresenter(){
        return new UserListPresenter(myApplication);
    }


    //ACTIVITY HERE

}
