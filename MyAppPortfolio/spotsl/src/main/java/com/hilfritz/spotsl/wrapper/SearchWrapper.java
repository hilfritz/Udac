
package com.hilfritz.spotsl.wrapper;

import com.google.gson.annotations.Expose;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class SearchWrapper {

    @Expose
    private Artists artists;

    /**
     * 
     * @return
     *     The artists
     */
    public Artists getArtists() {
        return artists;
    }

    /**
     * 
     * @param artists
     *     The artists
     */
    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
