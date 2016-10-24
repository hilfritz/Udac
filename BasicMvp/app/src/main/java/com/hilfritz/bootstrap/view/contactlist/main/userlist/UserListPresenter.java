package com.hilfritz.bootstrap.view.contactlist.main.userlist;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.RestApiManager;
import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.application.MyApplication;
import com.hilfritz.bootstrap.framework.BaseActivity;
import com.hilfritz.bootstrap.framework.BaseFragment;
import com.hilfritz.bootstrap.framework.BasePresenter;
import com.hilfritz.bootstrap.framework.BasePresenterInterface;

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

public class UserListPresenter extends BasePresenter implements BasePresenterInterface{
    private static final String TAG = "UserListPresenter";
    Activity activity;
    UserListFragment fragment;
    Subscription loadUsersSubscription;
    Subscription sortUsersSubscription;
    List<UserWrapper> usersList = new ArrayList<UserWrapper>();
    public static final long DELAY = 200;

    public enum LOADING_TYPES {
        REGULAR,
        SORTING_AZ,
        SORTING_ZA
    }
    /**
     *                    <ul>
     *                      <li>0 - no loading</li>
     *                      <li>1 - sort az loading</li>
     *                      <li>2 - sort za loading</li>
     *                    </ul>
     */
    LOADING_TYPES loadingType = LOADING_TYPES.REGULAR;

    @Inject RestApiManager apiManager;

    public UserListPresenter(MyApplication myApplication){
        //INITIALIZE INJECTION
        myApplication.getAppComponent().inject(this);
    }

    public void sort(LOADING_TYPES sortMode){
        logd( "sort() ");
        if (sortMode==LOADING_TYPES.SORTING_AZ){
            showLoading(LOADING_TYPES.SORTING_AZ);
            sortUsersSubscription = getSortAzsubscribable()
                    .subscribe(getSortSubscriber());
        }else if (sortMode==LOADING_TYPES.SORTING_ZA){
            showLoading(LOADING_TYPES.SORTING_ZA);
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
                .delay(UserListPresenter.DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                ;
    }
    public Subscriber<Boolean> getSortSubscriber(){
        logd("getSortSubscriber() ");
        return new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                setLoadingType(null);
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
                fragment.notifyDataSetChanged();
                fragment.showList();
                logd("getSortSubscriber() onNext()");
            }
        };
    }
    public Observable<Boolean> getSortZasubscribable(){
        return Observable.just(sortZa())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .delay(UserListPresenter.DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
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
        fragment.notifyDataSetChanged();
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
    public void showLoading(LOADING_TYPES loadingType){
        logd("showLoading():"+loadingType);
        setLoadingType(loadingType);
        switch (loadingType){
            case REGULAR:
                //loading from server
                fragment.showLoading(fragment.getString(R.string.loading_users));
                break;
            case SORTING_AZ:
                //sort az
                fragment.showLoading(fragment.getString(R.string.sort_az_load));
                break;
            case SORTING_ZA:
                //sort za
                fragment.showLoading(fragment.getString(R.string.sort_za_load));
                break;
            default:
                fragment.setLoadingVisibility(View.GONE);
                break;
        }
    }

    public void logd(String str){
        Log.d(TAG,TAG+">>"+str);
    }

    public Subscriber<List<UserWrapper>> getUsersSubscriber(){
        return new Subscriber<List<UserWrapper>>() {
            @Override
            public void onCompleted() {
                setLoadingType(null);
                loadUsersSubscription = null;
                fragment.setLoadingVisibility(View.GONE);
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
        fragment.notifyDataSetChanged();
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
                (getLoadingType()==LOADING_TYPES.SORTING_AZ || getLoadingType()==LOADING_TYPES.SORTING_ZA || getLoadingType()==LOADING_TYPES.REGULAR) &&
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

    public LOADING_TYPES getLoadingType() {
        return loadingType;
    }

    public void setLoadingType(LOADING_TYPES loadingType) {
        logd("setLoadingType:"+loadingType);
        this.loadingType = loadingType;
    }

    @Override
    public void bpi_init(BaseActivity activity, BaseFragment fragment) {
        this.activity = activity;
        this.fragment = (UserListFragment) fragment;
        if (bp_isInitialLoad()==true) {
            logd("init() for new activity");
            loadingType = LOADING_TYPES.REGULAR;
            usersList.clear();
            //FRAMEWORK
            /**
             * DO YOUR UI INTERFACE PROCESSES HERE, TAKE NOTE IF IT IS FROM ORIENTATION CHANGE OR
             * FROM A COMPLETELY NEW FRAGMENT
             * YOU HAVE TO CHECK IF INITIAL LOAD OR NOT
             */
            populate();
        }else{
            logd("init() for orientation change");
            if (getLoadingType()==null)
                ((UserListFragment) fragment).setLoadingVisibility(View.GONE);
        }

    }

    public List<UserWrapper> getUsersList() {
        return usersList;
    }

    @Override
    public void bpi_reset() {

    }
}
