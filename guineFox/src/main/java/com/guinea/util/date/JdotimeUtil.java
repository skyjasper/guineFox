package com.guinea.util.date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by shiky on 2015/6/14.
 */
public class JdotimeUtil {
    /****
     * 字符串转换为日期datetime
     *
     * @return
     */
    public static DateTime getDateTimeForStr(String strDate,String fmt) {
        DateTime dateTime = null;
        if (StringUtils.isBlank(fmt)) {
            fmt = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        if (StringUtils.isNotBlank(strDate)) {
            String temp = StringUtils.trim(strDate);
            DateTimeFormatter format = DateTimeFormat.forPattern(fmt);
            dateTime = DateTime.parse(temp, format);
        }
        return dateTime;
    }

    /****
     * 字符串转换为日期
     *
     * @return
     */
    public static Date getDateForStr(String strDate) {
        Date date = null;
        if (StringUtils.isNotBlank(strDate)) {
            String temp = StringUtils.trim(strDate);
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
            DateTime dateTime = DateTime.parse(temp, format);
            date = dateTime.toDate();
        }
        return date;
    }

    /****
     * 字符串转换为日期
     *
     * @param strDate
     * @param formatstr
     * @return
     */
    public static Date getDateForStr(String strDate, String formatstr) {
        Date date = null;
        if (StringUtils.isBlank(formatstr)) {
            formatstr = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        if (StringUtils.isNotBlank(strDate)) {
            String temp = StringUtils.trim(strDate);
            DateTimeFormatter format = DateTimeFormat.forPattern(formatstr);
            DateTime dateTime = DateTime.parse(temp, format);
            date = dateTime.toDate();
        }
        return date;
    }

    /***
     * 日期转换为字符串
     *
     * @param date
     * @param fmt
     * @return
     */
    public static String getDateToStr(Date date, String fmt) {
        String dateStr = "";
        if (StringUtils.isBlank(fmt)) {
            fmt = "yyyy-MM-dd HH:mm:ss";
        }
        if (null != date) {
            DateTime dateTime = new DateTime(date);
            dateStr = dateTime.toString(fmt);
        }
        return dateStr;
    }

    /***
     * 日期转换为字符串
     * @param dateTime
     * @param fmt
     * @return
     */
    public static String getDateToStr(DateTime dateTime, String fmt) {
        String dateStr = "";
        if (StringUtils.isBlank(fmt)) {
            fmt = "yyyy-MM-dd HH:mm:ss";
        }
        if (null != dateTime) {
            dateStr = dateTime.toString(fmt);
        }
        return dateStr;
    }
}
