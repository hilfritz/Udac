package com.hilfritz.bootstrap.eventbus.deligate;

import com.hilfritz.bootstrap.eventbus.event.SortEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class SortEventDeligate {
    public static void sortAz(){
        EventBus.getDefault().post(new SortEvent(SortEvent.MODE_SORT_AZ));
    }
    public static void sortZa(){
        EventBus.getDefault().post(new SortEvent(SortEvent.MODE_SORT_ZA));
    }
}
