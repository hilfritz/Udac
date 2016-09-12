package com.hilfritz.bootstrap.view.contactlist.main.userlist;

import android.view.View;

import com.hilfritz.bootstrap.view.BaseView;

/**
 * Created by Hilfritz P. Camallere on 6/28/2016.
 */

public interface UserListView extends BaseView {

    public void showMessage(String str, View.OnClickListener clickListener);
    public void showLoading(String str);
    public void showLoading();
    public void showList();
    public void sortZa();
    public void sortAz();
    public void toast(String str);

}
