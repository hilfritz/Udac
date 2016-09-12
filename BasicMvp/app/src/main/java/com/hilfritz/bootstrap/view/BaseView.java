package com.hilfritz.bootstrap.view;

import android.os.Bundle;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 * This is the basic interface for all Views in this project's MVP code structure
 */

public interface BaseView {
    /**
     * IMPORTANT: this must be called in {@link android.app.Fragment#onSaveInstanceState(Bundle)}
     * <br/> do your data saving here!
     */
    public void saveDataOnOrientationChange(Bundle savedInstanceState);

    /**
     * IMPORTANT: this must be called in {@link android.app.Fragment#onCreate(Bundle)}
     * <br/> helps you determin if the call of the {@link android.app.Fragment#onCreate(Bundle)} method is either
     * <ul>
     *     <li>from a rotation change - because {@link android.app.Fragment#onCreate(Bundle)} can still be called if there is orientation change</li>
     *     <li>from scratch/from a call of startActivity</li>
     * </ul>
     *
     * <br/>
     * Should be called like this:
     * <pre>
     *     {@code
     *     @Override
     *     public void onCreate(Bundle savedInstanceState) {
     *      super.onCreate(savedInstanceState);
     *      logd("onCreate: ");
     *      ...
     *      checkIfNewActivity(savedInstanceState); //>>   THIS PART
     *      ...
     *      if (getArguments() != null) {
     *          mParam1 = getArguments().getString(ARG_PARAM1);
     *          mParam2 = getArguments().getString(ARG_PARAM2);
     *      }
     *     }
     *
     *     }
     *
     * </pre>
     *
     * <br/>
     * The content code should be like this:
     * <pre>
     *     {@code
     *     if (savedInstanceState==null){
     *      logd("new activity/fragment");
     *      presenter.setInitialLoad(true);
     *     }else {
     *      logd("orientation change");
     *      presenter.setInitialLoad(false);
     *         }
     *     }
     * </pre>
     */
    //public void checkIfNewActivity(Bundle savedInstanceState);

}
