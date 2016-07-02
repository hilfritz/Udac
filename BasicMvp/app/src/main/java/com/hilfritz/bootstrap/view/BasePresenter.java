package com.hilfritz.bootstrap.view;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class BasePresenter {
    /**
     *
     */
    boolean initialLoad = true;

    public boolean isInitialLoad() {
        return initialLoad;
    }

    public void setInitialLoad(boolean initialLoad) {
        this.initialLoad = initialLoad;
    }
}
