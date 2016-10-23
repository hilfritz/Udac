package com.hilfritz.bootstrap.util;

/**
 * Created by hilfritz on 8/1/16.
 */

public class StringUtil {
    public static boolean isStringEmpty(String str){
        boolean retVal = false;
        if (str!=null){
            if (str.length()>0){
                if (!str.equalsIgnoreCase("null")){
                    retVal = true;
                }
            }
        }
        return retVal;
    }
}
