package com.hilfritz.bootstrap.view.contactlist.main.userlist;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.RestApiManager;
import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.application.MyApplication;
import com.hilfritz.bootstrap.view.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 * The presenter for the fragment list
 */

public class UserListPresenter extends BasePresenter{
    public static final int SORT_AZ = 0;
    public static final int SORT_ZA = 1;

    Activity activity;
    UserListFragment fragment;
    Subscription loadUsersSubscription;
    Subscription sortUsersSubscription;
    UserListAdapter adapter;
    List<UserWrapper> usersList;
    /**
     *                    <ul>
     *                      <li>0 - no loading</li>
     *                      <li>1 - sort az loading</li>
     *                      <li>2 - sort za loading</li>
     *                    </ul>
     */
    int loadingType = 0;

    private static final String TAG = "UserListPresenter";

    @Inject RestApiManager apiManager;

    public UserListPresenter(MyApplication myApplication){
        myApplication.getAppComponent().inject(this);
    }

    public void init(Activity activity, UserListFragment fragment) {
        logd("init()");
        this.activity = activity;
        this.fragment = fragment;
        if (isInitialLoad()==true) {
            logd("init() for new activity");
            loadingType = 0;
            usersList = new ArrayList<UserWrapper>();
            adapter = new UserListAdapter(this.fragment.getContext(), usersList);
        }else{
            logd("init() for orientation change");
        }
        //IMPORTANT
        //SET YOUR ADAPTERS HERE
        fragment.getListView().setAdapter(adapter);
    }

    public void sort(int sortMode){
        logd( "sort() ");
        if (sortMode==UserListPresenter.SORT_AZ){
            showLoading(2);
            sortUsersSubscription = getSortAzsubscribable()
                    .subscribe(getSortSubscriber());
        }else if (sortMode==UserListPresenter.SORT_ZA){
            showLoading(3);
            sortUsersSubscription =getSortZasubscribable()
                    .subscribe(getSortSubscriber());
        }
    }

    public boolean sortAz(){
        logd("sortAz() ");
        Collections.sort(usersList, new Comparator<UserWrapper>() {
            @Override
            public int compare(UserWrapper lhs, UserWrapper rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });
        return true;
    }

    public Observable<Boolean> getSortAzsubscribable(){
        return Observable.just(sortAz())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .delay(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                ;
    }
    public Subscriber<Boolean> getSortSubscriber(){
        logd("getSortSubscriber() ");
        return new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                setLoadingType(0);
                sortUsersSubscription = null;
            }

            @Override
            public void onError(Throwable e) {
                String message = fragment.getString(R.string.error_loading_listview2, e.getLocalizedMessage())+" "+fragment.getString(R.string.try_again);
                fragment.showMessage(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        populate();
                    }
                });
            }

            @Override
            public void onNext(Boolean aBoolean) {
                adapter.notifyDataSetChanged();
                fragment.showList();
                logd("getSortSubscriber() onNext()");
            }
        };
    }
    public Observable<Boolean> getSortZasubscribable(){
        return Observable.just(sortZa())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .delay(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                ;

    }
    public boolean sortZa(){
        logd("sortZa() ");
        Collections.sort(usersList, new Comparator<UserWrapper>() {
            @Override
            public int compare(UserWrapper lhs, UserWrapper rhs) {
                return rhs.getName().toLowerCase().compareTo(lhs.getName().toLowerCase());
            }
        });
        return true;
    }




    public void populate() {
        Log.d(TAG, "populate()");

        adapter.notifyDataSetChanged();
        showLoading(1);
        //CALL API
        loadUsersSubscription = apiManager.getUsersSubscribable()
                .subscribe(getUsersSubscriber());
    }

    /**
     *
     * @param loadingType int
     *                    <ul>
     *                      <li>0 - no loading</li>
     *                      <li>1 - sort az loading</li>
     *                      <li>2 - sort za loading</li>
     *                    </ul>
     */
    public void showLoading(int loadingType){
        logd("showLoading():"+loadingType);
        setLoadingType(loadingType);
        switch (loadingType){
            case 0:
                //no loading
                break;
            case 1:
                //loading from server
                fragment.showLoading(fragment.getString(R.string.loading_users));
                break;
            case 2:
                //sort az
                fragment.showLoading(fragment.getString(R.string.sort_az_load));
                break;
            case 3:
                //sort za
                fragment.showLoading(fragment.getString(R.string.sort_za_load));
                break;
            case 4:
                //no loading
                break;
        }
    }

    public void logd(String str){
        Log.d(TAG,str);
    }

    public Subscriber<List<UserWrapper>> getUsersSubscriber(){
        return new Subscriber<List<UserWrapper>>() {
            @Override
            public void onCompleted() {
                setLoadingType(0);
                loadUsersSubscription = null;
            }

            @Override
            public void onError(Throwable e) {
                // cast to retrofit.HttpException to get the response code
                if (e instanceof HttpException) {
                    HttpException response = (HttpException)e;
                    int code = response.code();
                }
                logd ("getUsersSubscriber() onError() error loading");
                String message = fragment.getString(R.string.error_loading_listview, e.getLocalizedMessage())+" "+fragment.getString(R.string.try_again);
                fragment.showMessage(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        populate();
                    }
                });
            }

            @Override
            public void onNext(List<UserWrapper> userList) {
                if (userList!=null && userList.size()>0){
                    showUsersList(userList);
                    logd("getUsersSubscriber() onNext() adapter.notifyDataSetChanged() userList.size():"+userList.size());
                }

                fragment.showList();
                logd("getUsersSubscriber() onNext()");
            }
        };
    }

    /**
     * THIS IS HOW TO PROPERLY POPULATE A LIST
     * <UL>
     *     <LI>CLEAR THE LIST, ADD THE NEW LIST TO THE EXISTING LIST</LI>
     *     <LI>CALL NOTIFYDATASETCHANGED</LI>
     *     <LI>CALL VIEW'S SHOWLIST METHOD</LI>
     * </UL>
     *
     * @param userList
     */
    private void showUsersList(List<UserWrapper> userList) {
        usersList.clear();
        usersList.addAll(userList);
        adapter.notifyDataSetChanged();
        fragment.listViewItemClick(usersList);
        fragment.showList();
    }

    public void onDestroy() {
        logd ("onDestroy()");
        /*
        if (loadUsersSubscription!=null && loadUsersSubscription.isUnsubscribed()==false){
            loadUsersSubscription.unsubscribe();
        }
        */
    }

    public boolean isOnGoingSortingUsers(){
        boolean retVal = false;
        if (sortUsersSubscription!=null && sortUsersSubscription.isUnsubscribed()==false){
            retVal = true;
        }
        return retVal;
    }

    public boolean isOnGoingLoadingUsers(){
        boolean retVal = false;
        if (loadUsersSubscription!=null && loadUsersSubscription.isUnsubscribed()==false){
            retVal = true;
        }
        return retVal;
    }


    public void continuePreviousProcess() {
        logd("continuePreviousProcess() usersList.size():"+usersList.size()+" getLoadingType():"+getLoadingType());

        /**
         * CHECK ALL YOUR SUBSCRIPTIONS HERE,
         * <BR/>IF THEY ARE RUNNING,  SHOW THE LOADING
         * <BR/> - RUNNING - CALLING API, DOWNLOADING, ETC THAT NEEDS A LOADING UI
         */

        if (
                (getLoadingType()==1 || getLoadingType()==2 || getLoadingType()==3) &&
                        (isOnGoingLoadingUsers()==true || isOnGoingSortingUsers()==true)
                ) {
            showLoading(getLoadingType());
            logd("continuePreviousProcess() return");
            return;
        }

        /**
         * IF ALL YOUR SUBSCRIPTIONS ARE FINISHED DOING, POPULATE VIEW WITH THE RETRIEVED DATA
         * <BR/> FINISHED - NO MORE ONGOING API CALLS, DOWNLOADS, ETC THAT NEEDS A LOADING UI
         */

        ArrayList<UserWrapper> temp = new ArrayList<UserWrapper>();
        temp.addAll(usersList);
        showUsersList(temp);



        logd("continuePreviousProcess() usersList.size():"+usersList.size()+"");
        logd("continuePreviousProcess() temp.size():"+temp.size()+"");

    }

    public int getLoadingType() {
        return loadingType;
    }

    public void setLoadingType(int loadingType) {
        logd("setLoadingType:"+loadingType);
        this.loadingType = loadingType;
    }
}
