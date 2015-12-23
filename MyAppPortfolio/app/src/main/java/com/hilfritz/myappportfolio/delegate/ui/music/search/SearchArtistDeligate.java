package com.hilfritz.myappportfolio.delegate.ui.music.search;

import com.hilfritz.myappportfolio.eventbus.BusProvider;
import com.hilfritz.myappportfolio.eventbus.SearchArtistEvent;
import com.hilfritz.spotsl.wrapper.Item;

/**
 * Created by Hilfritz P. Camallere on 12/23/2015.
 */
public class SearchArtistDeligate {
    //BusProvider.getInstance().post(new SearchArtistEvent(SearchArtistEvent.SHOW_RESULT_LIST_FOR_TABLET,item));
    //BusProvider.getInstance().post(new SearchArtistEvent(SearchArtistEvent.CLEAR));

    public static void clearList(){
        BusProvider.getInstance().post(new SearchArtistEvent(SearchArtistEvent.CLEAR));
    }
    public static void showSearchResultForTab(Item item){
        BusProvider.getInstance().post(new SearchArtistEvent(SearchArtistEvent.SHOW_RESULT_LIST_FOR_TABLET,item));
    }
}
