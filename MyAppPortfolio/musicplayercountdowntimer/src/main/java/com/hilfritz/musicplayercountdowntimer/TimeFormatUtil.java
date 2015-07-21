package com.hilfritz.musicplayercountdowntimer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author hilfritz.p.camallere on 6/19/2015.
 * @see http://stackoverflow.com/questions/20331163/how-to-format-joda-time-datetime-to-only-mm-dd-yyyy
 */
public class TimeFormatUtil {
    /**
     * For mm:ss format
     */
    public static final int MIN_SEC_FORMAT = 0;

    public static final int HR_MIN_SEC_FORMAT = 1;
    //format for mm:ss
    protected static DateTimeFormatter minSecFormat = DateTimeFormat.forPattern("mm:ss");
    //format for hh:mm:ss
    protected static DateTimeFormatter hrMinSecFormat = DateTimeFormat.forPattern("hh:mm:ss");

    /**
     *
     * @param milliseconds long
     * @param format int either
     *                      <ul>
     *                          <li>TimeFormatUtil.MIN_SEC_FORMAT</li>
     *                          <li>TimeFormatUtil.HR_MIN_SEC_FORMAT</li>
     *                      </ul>
     * @return String null if invalid format parameter
     */
    public static String getFormattedDateForDisplay(long milliseconds, int format){
        String retVal = null;
        Date date = new Date(milliseconds);
        DateTime dateTime = new DateTime(date);
        switch(format){
            case MIN_SEC_FORMAT :
                retVal = minSecFormat.print(dateTime);
            break;

            case HR_MIN_SEC_FORMAT:
                retVal = hrMinSecFormat.print(dateTime);//untested
            break;
        }
        return retVal;
    }

    public static int getSecondsFromMilliseconds(int milliseconds){
        int result = milliseconds/1000;
        return result;
    }

    public static int getMilliSecondsFromSeconds(int seconds){
        return seconds * 1000;
    }
}
