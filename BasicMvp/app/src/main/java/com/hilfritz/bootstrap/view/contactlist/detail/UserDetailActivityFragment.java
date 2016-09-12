package com.hilfritz.bootstrap.view.contactlist.detail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserDetailActivityFragment extends BaseFragment {

    public static final String EXTRA_USERWRAPPER = "userWrapperExtra";
    UserDetailFragmentPresenter presenter;
    @BindView(R.id.messageTextView)
    TextView messageTextView;

    @BindView(R.id.mainLinearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.value)
    TextView value;

    @BindView(R.id.value1)
    TextView value1;

    @BindView(R.id.value2)
    TextView value2;

    @BindView(R.id.value3)
    TextView value3;

    @BindView(R.id.value4)
    TextView value4;

    @BindView(R.id.value5)
    TextView value5;

    Gson gson;


    public UserDetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserDetailFragmentPresenter(getActivity(), this);
        gson = new Gson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageTextView.setText("...");
        linearLayout.setVisibility(View.GONE);
        //IF NEW ACTIVITY
        if (getActivity().getIntent().getStringExtra(UserDetailActivityFragment.EXTRA_USERWRAPPER) != null) {
            String extra = getActivity().getIntent().getStringExtra(UserDetailActivityFragment.EXTRA_USERWRAPPER);
            UserWrapper user =  gson.fromJson( extra, UserWrapper.class );
            presenter.populate(user);
        }else{
            //IF INSIDE THe MAIN ACTIVITY

        }
    }

    public UserWrapper getUserWrapper() {
        return null;
    }

    public void populate(UserWrapper userWrapper){
        linearLayout.setVisibility(View.VISIBLE);
        messageTextView.setVisibility(View.GONE);
        value.setText(userWrapper.getName());
        value1.setText(userWrapper.getUsername());
        value2.setText(userWrapper.getPhone());
        value3.setText(userWrapper.getAddress().getSuite()
                +" "+userWrapper.getAddress().getStreet()
                +" "+userWrapper.getAddress().getCity()
                +" "+userWrapper.getAddress().getZipcode()
                );
        value4.setText(userWrapper.getWebsite());
        value5.setText(
                Html.fromHtml(
                        userWrapper.getCompany().getName()
                                +" <br/>"+userWrapper.getPhone()
                                +" <br/>"+userWrapper.getEmail()
                                )
        );
    }
}
