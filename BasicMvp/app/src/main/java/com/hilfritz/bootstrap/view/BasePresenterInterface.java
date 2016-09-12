package com.hilfritz.bootstrap.view;

/**
 * Created by Hilfritz P. Camallere on 8/20/2016.
 */
public interface BasePresenterInterface {
    /**
     * IMPORTANT: CALL THIS IN Activity#OnCreate()
     * @param activity
     * @param fragment
     */
    public void _init(BaseActivity activity, BaseFragment fragment);

    public void _reset();
}
