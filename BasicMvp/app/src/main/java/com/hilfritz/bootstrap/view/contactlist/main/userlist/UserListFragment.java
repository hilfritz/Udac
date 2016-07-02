package com.hilfritz.bootstrap.view.contactlist.main.userlist;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.application.MyApplication;
import com.hilfritz.bootstrap.eventbus.deligate.UserListItemClickEventDeligate;
import com.hilfritz.bootstrap.eventbus.event.SortEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class UserListFragment extends Fragment implements UserListView{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "UserListFragment";

    @Inject UserListPresenter presenter;


    public static final String EXTRA_VIEW_STATE = "extraViewState";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.messageTextView)
    TextView messageTextView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.userListView)
    ListView listView;



    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance(String param1, String param2) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        logd("onResume: ");
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logd("onCreate: ");
        (((MyApplication) getActivity().getApplication()).getAppComponent()).inject(this);
        checkIfNewActivity(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void checkIfNewActivity(Bundle savedInstanceState) {
        if (savedInstanceState==null){
            logd("new activity/fragment");
            presenter.setInitialLoad(true);
        }else {
            logd("orientation change");
            presenter.setInitialLoad(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logd("onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logd("onViewCreated: ");
        /**
         * IMPORTANT: PLACE THE INIT HERE
         */
        presenter.init(getActivity(), this);
        /**
         * DO YOUR UI INTERFACE PROCESSES HERE, TAKE NOTE IF IT IS FROM ORIENTATION CHANGE OR
         * FROM A COMPLETELY NEW FRAGMENT
         * YOU HAVE TO CHECK IF INITIAL LOAD OR NOT
         */
        if (presenter.isInitialLoad())
            presenter.populate();
        else{
            //REPOPULATE LIST
            //FROM ORIENTATION CHANGE
            presenter.continuePreviousProcess();
        }
    }

    public void showMessage(String str){
        listView.setVisibility(View.GONE);
        messageTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String str, View.OnClickListener onClickListener){
        listView.setVisibility(View.GONE);
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(str);
        if (onClickListener!=null){
            messageTextView.setOnClickListener(onClickListener);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading(){
        logd("showLoading: ");
        listView.setVisibility(View.GONE);
        messageTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void showLoading(String message){
        logd("showLoading() message ");
        listView.setVisibility(View.GONE);
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(message);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList(){
        listView.setVisibility(View.VISIBLE);
        messageTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void sortZa() {

    }

    @Override
    public void sortAz() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logd("onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logd("onDetach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logd("onDestroy: ");
        presenter.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sortClickListener(SortEvent sortEvent) {
        if (sortEvent.getMode()==SortEvent.MODE_SORT_AZ)
            presenter.sort(UserListPresenter.SORT_AZ);
        else if (sortEvent.getMode()==SortEvent.MODE_SORT_ZA)
            presenter.sort(UserListPresenter.SORT_ZA);
    }

    public ListView getListView() {
        return listView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        logd("onConfigurationChanged: ");
    }

    private void logd(String msg) {
        Log.d(TAG, TAG+">> "+msg);
    }

    @Override
    public void toast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveDataOnOrientationChange(Bundle savedInstanceState) {
        //savedInstanceState.putInt(EXTRA_VIEW_STATE, -1);
        logd("saveDataOnOrientationChange");
    }

    public void listViewItemClick(final List<UserWrapper> userList){
        logd("listViewItemClick()");
        if (userList.size() > 0) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    logd("listViewItemClick() onItemClick()");
                    UserWrapper userWrapper = userList.get(position);
                    UserListItemClickEventDeligate.userlistItemClick(userWrapper);
                }
            });
        }
    }
}
