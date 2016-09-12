package com.hilfritz.bootstrap.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Hilfritz P. Camallere on 8/20/2016.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void checkIfNewActivity(Bundle savedInstanceState, BasePresenter presenter) {
        Log.d(TAG, "checkIfNewActivity: ");
        if (savedInstanceState==null){
            Log.d(TAG, "checkIfNewActivity: presenter INITIAL LOAD");
            presenter.setInitialLoad(true);
        }else {
            Log.d(TAG, "checkIfNewActivity: presenter from ROTATION");
            presenter.setInitialLoad(false);
        }
    }
}
