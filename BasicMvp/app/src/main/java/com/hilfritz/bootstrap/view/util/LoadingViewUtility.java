package com.hilfritz.bootstrap.view.util;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class LoadingViewUtility {
    boolean isLoading = false;
    String message = "";

    public LoadingViewUtility(boolean isLoading, String message) {
        this.isLoading = isLoading;
        this.message = message;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
