package com.hilfritz.bootstrap.dagger2.component;

import com.hilfritz.bootstrap.dagger2.module.SessionModule;
import com.hilfritz.bootstrap.dagger2.module.UtilityModule;
import com.hilfritz.bootstrap.dagger2.module.ViewPresenterModule;
import com.hilfritz.bootstrap.dagger2.module.RestApiModule;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListFragment;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */
@Singleton
@Component(modules = {
        RestApiModule.class,
        ViewPresenterModule.class,
        UtilityModule.class,
        SessionModule.class,
})
public interface AppComponent {
    void inject(UserListFragment fragment);
    void inject(UserListPresenter presenter);

}
