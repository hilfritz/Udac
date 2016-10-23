package com.hilfritz.bootstrap.util;

import android.content.Context;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hilfritz on 8/1/16.
 */
public class DateUtil {
    public static final String dateFormatA = "MM/dd/yyyy";

    public static final String DATEFORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     *
     * @param timezone String ex. "Asia/Singapore"
     * @param dateTime DateTime the DateTime object to format
     * @param dateFormat String ex yyyy-mm-dd
     * @return String the formated date
     */
    public static String formatDate(String timezone, DateTime dateTime, String dateFormat){
        DateTimeZone zone = DateTimeZone.forID(timezone);
        DateTime zoned = dateTime.withZone(zone);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormat).withZone(zone);
        return fmt.print(zoned);
    }
    /**
     *
     * @param timeZone DateTimeZone ex. "Asia/Singapore"
     * @param dateTime DateTime the DateTime object to format
     * @param dateFormat String ex yyyy-mm-dd
     * @return String the formated date
     */
    public static String formatDate(DateTimeZone timeZone, DateTime dateTime, String dateFormat){
        DateTime zoned = dateTime.withZone(timeZone);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormat).withZone(timeZone);
        return fmt.print(zoned);
    }

    /**
     *
     * @param dateTime DateTime the DateTime object to format
     * @param dateFormat String "yyyy-MM-dd'T'HH:mm:ss'Z'"
     * @return String the formated date
     */
    public static String formatDateToUTC(DateTime dateTime, String dateFormat){;
        DateTimeFormatter fmt = DateTimeFormat.forPattern(dateFormat).withZoneUTC();
        return fmt.print(dateTime);
    }

    /**
     *
     * @param dateToBeConverted String UTC format
     * @param FromDateFormat
     * @param ToDateFormat
     * @param timeZone
     * @return
     */
    public static String convertToFormat2(String dateToBeConverted, String FromDateFormat, String ToDateFormat, String timeZone) {

        SimpleDateFormat formatter = new SimpleDateFormat(FromDateFormat);
        SimpleDateFormat formatter2 = new SimpleDateFormat(ToDateFormat);
        String currentDate = "";
        Date date;

        try {
            formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
            date = formatter.parse(dateToBeConverted);

            currentDate = formatter.format(date);
            formatter2.setTimeZone(TimeZone.getTimeZone("UTC"));
            currentDate = formatter2.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return currentDate;
    }



    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;



    /**
     *
     * @param time long timestamp from Date or DateTime
     * @param ctx
     * @param now System.currentTimeMillis()
     * @return
     */
    public static String getTimeAgo(long time, Context ctx, long now) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }


    public static DateTime createDateTimeObject(int day, int month, int year, String timezoneStr){
        //get the timezone of the location, here we just explicitly set to Asia/Singapore
        DateTimeZone timezone = DateTimeZone.forID(timezoneStr);
        //create the datetiime object
        DateTime dateTime = new DateTime(year,month,day,0,0,timezone);
        return dateTime;
    }

}
