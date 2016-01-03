package com.hilfritz.myappportfolio.ui;

/**
 * Created by Hilfritz P. Camallere on 12/20/2015.
 */
public interface BaseActivityInterface {

    /**
     * Called after Activity#onCreate().super() is called
     */
    void init();

    /**
     * This is called only once
     */
    void afterInitViews();
}
