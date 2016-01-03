package com.hilfritz.myappportfolio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Hilfritz P. Camallere on 6/13/2015.
 */
public abstract class BaseFragment extends Fragment implements BaseFragmentInterface{

    boolean firstLoad = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterInitViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (firstLoad){
            //afterInitViews();
            firstLoad = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
