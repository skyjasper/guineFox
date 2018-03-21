package com.guinea.util.date;

import com.guinea.util.Constents;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by shiky on 2015/6/14.
 */
public class JodatimeUtil {

    public final static String fmtSSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public final static String simfmtSSS = "yyyyMMddHHmmssSSS";

    public final static String fmtSS = "yyyy-MM-dd HH:mm:ss";

    public final static String simfmtSS = "yyyyMMddHHmmss";

    public final static String fmtDD = "yyyy-MM-dd";

    public final static String simfmtDD = "yyyyMMdd";

    public final static String defaultHHMMSS_s = "000000";

    public final static String defaultHHMMSS_e = "235930";


    /****
     * 字符串转换为日期datetime
     *
     * @return
     */
    public static DateTime getDateTimeForStr(String strDate, String fmt) {
        DateTime dateTime = null;
        if (StringUtils.isBlank(fmt)) {
            fmt = fmtSSS;
        }
        if (StringUtils.isNotBlank(strDate)) {
            String temp = StringUtils.trim(strDate);
            DateTimeFormatter format = DateTimeFormat.forPattern(fmt);
            dateTime = DateTime.parse(temp, format);
        }
        return dateTime;
    }

    /***
     * 字符串转化为DateTime，带 “-”
     *
     * @param strDate
     * @return
     */
    public static DateTime getDateTimeForStrSS(String strDate) {
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(strDate)) {
            String temp = StringUtils.trim(strDate);
            DateTimeFormatter format = DateTimeFormat.forPattern(fmtSS);
            dateTime = DateTime.parse(temp, format);
        }
        return dateTime;
    }

    /****
     * 字符串转换为日期,到毫秒，有"-"
     *
     * @return
     */
    public static Date getDateForStr(String strDate) {
        Date date = null;
        if (StringUtils.isNotBlank(strDate)) {
            String temp = StringUtils.trim(strDate);
            DateTimeFormatter format = DateTimeFormat.forPattern(fmtSSS);
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
            formatstr = fmtSSS;
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
            fmt = fmtSS;
        }
        if (null != date) {
            DateTime dateTime = new DateTime(date);
            dateStr = dateTime.toString(fmt);
        }
        return dateStr;
    }

    /***
     * dateTime日期转换为字符串
     *
     * @param dateTime
     * @param fmt
     * @return
     */
    public static String getDateTimeToStr(DateTime dateTime, String fmt) {
        if (StringUtils.isBlank(fmt)) {
            fmt = fmtSS;
        }
        dateTime.toString(fmt);
        return dateTime.toString(fmt);
    }

    /***
     * dateTime日期转换为字符串,到日无"-"
     *
     * @param dateTime
     * @return
     */
    public static String getDateToStr(DateTime dateTime) {
        String dateStr = "";
        if (null != dateTime) {
            dateStr = dateTime.toString(simfmtDD);
        }
        return dateStr;
    }

    /***
     * 星期
     *
     * @param dt
     * @return
     */
    public static String getWeekDay(DateTime dt) {
        String resStr = "";
        switch (dt.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                resStr = Constents.SUNDAY;
                break;
            case DateTimeConstants.MONDAY:
                resStr = Constents.MONDAY;
                break;
            case DateTimeConstants.TUESDAY:
                resStr = Constents.TUESDAY;
                break;
            case DateTimeConstants.WEDNESDAY:
                resStr = Constents.WEDNESDAY;
                break;
            case DateTimeConstants.THURSDAY:
                resStr = Constents.THURSDAY;
                break;
            case DateTimeConstants.FRIDAY:
                resStr = Constents.FRIDAY;
                break;
            case DateTimeConstants.SATURDAY:
                resStr = Constents.SATURDAY;
                break;
        }
        return resStr;
    }

    /***
     * 得到两时间之间的分钟数
     * @param start
     * @param end
     * @return
     */
    public static int getTimeMin(DateTime start,DateTime end){
        Period p = new Period(start, end, PeriodType.minutes());
        return p.getMinutes();
    }

    /***
     * 得到两时间之间的毫秒数
     * @param start
     * @param end
     * @return
     */
    public static Long getTimeMillis(DateTime start,DateTime end){
        Duration d = new Duration(start, end);
        return d.getMillis();
    }

    public static void testDruaDays(DateTime start1,DateTime end1){
        DateTime start = new DateTime("2012-02-01");
        DateTime end = new DateTime("2012-05-01");
        Duration d = new Duration(start, end);
        long time = d.getMillis();//得到毫秒数
        System.out.println("time:"+time);
       //计算区间天数
        Period p = new Period(start, end, PeriodType.days());
        int days = p.getDays();
        System.out.println("days:"+days);
        //计算特定日期是否在该区间内
        Interval i = new Interval(start, end);
        boolean contained = i.contains(new DateTime("2012-03-01"));
        System.out.println("contained:"+contained);
    }
}
