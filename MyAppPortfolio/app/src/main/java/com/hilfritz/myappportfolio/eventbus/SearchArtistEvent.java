package com.hilfritz.myappportfolio.eventbus;

import com.hilfritz.spotsl.wrapper.Item;

/**
 * Created by Hilfritz P. Camallere on 12/23/2015.
 */
public class SearchArtistEvent {
    public static final int CLEAR = 0;
    public static final int SEARCH = 1;
    //for tablets only
    public static final int SHOW_RESULT_LIST_FOR_TABLET = 2;

    Item item;
    int mode = -1;



    public SearchArtistEvent(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public SearchArtistEvent(int mode,Item item) {
        this.item = item;
        this.mode = mode;
    }

    public Item getItem() {
        return item;
    }
}
