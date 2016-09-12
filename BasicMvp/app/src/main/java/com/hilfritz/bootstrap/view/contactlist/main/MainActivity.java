package com.hilfritz.bootstrap.view.contactlist.main;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.eventbus.deligate.SortEventDeligate;
import com.hilfritz.bootstrap.eventbus.event.UserListItemClickEvent;
import com.hilfritz.bootstrap.view.BaseActivity;
import com.hilfritz.bootstrap.view.contactlist.detail.UserDetailActivity;
import com.hilfritz.bootstrap.view.contactlist.detail.UserDetailActivityFragment;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static final String TAG = "MainActivity";

    boolean isLandscape = false;

    UserListFragment userListFragment;
    UserDetailActivityFragment userDetailActivityFragment;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        logd("onConfigurationChanged: ");
        checkScreenOrientation();
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        logd("onResume: ");
        checkScreenOrientation();

        //get the fragment
        //need to add this here because the orientation can loose the fragment if this is placed on "onCreate()"
        userListFragment = (UserListFragment)getSupportFragmentManager().findFragmentById(R.id.userListFragment);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserListItemClickEvent(UserListItemClickEvent userListItemClickEvent){
        checkScreenOrientation();
        if (isLandscape ==false){
            logd("onListItemClick() portrait");
            //START A NEW ACTIVITY FOR DETAILS
            UserDetailActivity.start(MainActivity.this, userListItemClickEvent.getUserWrapper());
        }else{
            logd("onListItemClick() is landscape");
            //SHOw THe DETAIL ON THE RIGHT SIDE
            userDetailActivityFragment = (UserDetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.userDetailFragment);
            userDetailActivityFragment.populate(userListItemClickEvent.getUserWrapper());
        }
    }

    private void logd(String msg) {
        Log.d(TAG, TAG+">> "+msg);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logd("onCreate: ");
        ButterKnife.bind(this);
        checkScreenOrientation();

    }

    private void checkScreenOrientation() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            isLandscape = true;
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            isLandscape = false;
        }
        logd("checkScreenOrientation() "+(isLandscape==true?" landscape":"portrait"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.sortAz){
            logd("onOptionsItemSelected() sort AZ");
            SortEventDeligate.sortAz();
        }else if (item.getItemId()==R.id.sortZa){
            logd("onOptionsItemSelected() sort ZA");
            SortEventDeligate.sortZa();
        }
        return true;
    }

}
