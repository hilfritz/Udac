package com.hilfritz.myappportfolio.delegate.ui.music.topten;

import android.view.View;

import com.hilfritz.myappportfolio.eventbus.BusProvider;
import com.hilfritz.myappportfolio.eventbus.SearchArtistEvent;
import com.hilfritz.myappportfolio.eventbus.TopTenTracksEvent;
import com.hilfritz.spotsl.wrapper.Track;

/**
 * Created by Hilfritz P. Camallere on 12/23/2015.
 */
public class TopTenTracksEventDelegate{
    public static void showMusicPlayer(int selectedIndex, Track track){
        BusProvider.getInstance().post(new TopTenTracksEvent(TopTenTracksEvent.SHOW_MUSIC_PLAYER, selectedIndex, track));
    }

}
