package com.hilfritz.myappportfolio.eventbus;

import com.hilfritz.spotsl.wrapper.Track;

/**
 * Created by Hilfritz P. Camallere on 12/23/2015.
 */
public class TopTenTracksEvent {
    public static final int SHOW_MUSIC_PLAYER = 0;

    public int selectedIndex = -1;
    Track selectedTrack;
    public int mode = -1;

    public TopTenTracksEvent(int mode) {
        this.mode = mode;
    }

    public TopTenTracksEvent(int mode, int selectedIndex, Track selectedTrack) {
        this.selectedIndex = selectedIndex;
        this.selectedTrack = selectedTrack;
        this.mode = mode;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public int getMode() {
        return mode;
    }
}
