package com.hilfritz.favoriteplacesmodule;

import com.hilfritz.favoriteplacesmodule.model.database.DatabaseConstants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Hilfritz P. Camallere on 1/2/2016.
 * @see http://stackoverflow.com/questions/20331163/how-to-format-joda-time-datetime-to-only-mm-dd-yyyy
 */
public class JodaTimeDateTimeUtil {
    public static final String FORMAT_1 = DatabaseConstants.DATABASE_DATETIME_FORMAT_1;

    public static final DateTimeFormatter dateTimeFormatter1 = DateTimeFormat.forPattern(FORMAT_1);


    public static DateTime formatDateStrToDateTime(String dateTimeStr, String format){
        DateTime jodatime = dateTimeFormatter1.parseDateTime(dateTimeStr);
        return jodatime;
    }
}
