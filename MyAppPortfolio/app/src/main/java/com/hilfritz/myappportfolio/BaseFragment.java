package com.hilfritz.myappportfolio;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.hilfritz.myappportfolio.tools.ConnectionUtil;

import butterknife.ButterKnife;

/**
 * Created by Hilfritz P. Camallere on 6/13/2015.
 */
public abstract class BaseFragment extends Fragment {
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * TODO: clean this method up. this is unused
     * @return boolean true if there is a network and internet connection, otherwise false
     *
     */
    public boolean checkInternet2(){
        if (ConnectionUtil.isNetworkAvailable(getActivity())==false){
            ((BaseActivity)getActivity()).longToast(getString(R.string.no_network));
            Log.d("checkInternet()", "no internet1");
            return false;
        }
        if (ConnectionUtil.hasActiveInternetConnection(getActivity())==false){
            ((BaseActivity)getActivity()).longToast(getString(R.string.no_internet));
            Log.d("checkInternet()", "no internet2");
            return false;
        }
        return true;
    }
}
