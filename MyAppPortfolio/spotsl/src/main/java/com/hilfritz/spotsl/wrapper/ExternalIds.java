
package com.hilfritz.spotsl.wrapper;

import com.google.gson.annotations.Expose;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class ExternalIds {

    @Expose
    private String isrc;

    /**
     * 
     * @return
     *     The isrc
     */
    public String getIsrc() {
        return isrc;
    }

    /**
     * 
     * @param isrc
     *     The isrc
     */
    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
