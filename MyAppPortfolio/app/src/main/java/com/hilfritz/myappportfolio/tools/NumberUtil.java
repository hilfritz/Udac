package com.hilfritz.myappportfolio.tools;

/**
 * Created by Hilfritz P. Camallere on 6/20/2015.
 */
public class NumberUtil {
    /**
     * @see http://stackoverflow.com/questions/1590831/safely-casting-long-to-int-in-java
     * @param l
     * @return
     */
    public static final int safeConvertLongToInt(long l){
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
