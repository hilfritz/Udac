package com.hilfritz.bootstrap.view;

/**
 * Created by Hilfritz P. Camallere on 8/20/2016.
 */
public interface BasePresenterInterface {
    /**
     * IMPORTANT: CALL THIS IN
     * <ul>
     *     <li>Activity#OnCreate()</li>
     *     <li>Fragment#onViewCreated</li>
     * </ul>
     * @param activity
     * @param fragment
     */
    public void bpi_init(BaseActivity activity, BaseFragment fragment);

    public void bpi_reset();
}
