package com.hilfritz.bootstrap.eventbus.event;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class SortEvent {
    int mode = -1;
    public static final int MODE_SORT_AZ = 0;
    public static final int MODE_SORT_ZA = 1;

    public SortEvent(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
}
