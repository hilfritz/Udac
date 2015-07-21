package com.hilfritz.myappportfolio.tools;

/**
 * Created by Hilfritz P. Camallere on 6/19/2015.
 */
public class StringUtil {
    public static boolean isNullOrEmptyString(String str){
        if (str ==null)
            return true;
        if (str.isEmpty()==true)
            return true;
        if (str.equalsIgnoreCase(""))
            return true;
        return false;
    }
}
