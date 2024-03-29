package com.goldenapple.lottery.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Gan on 2017/10/25.
 * 时间相关的工具类
 */

public class TimeUtils
{

    //某天的开始时间---获取当天的开始时间
    public static Date getBeginDateOfToday() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //某天的开始时间---获取昨天的开始时间
    public static Date getBeginDateOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    //某天的结束时间---获取当天的结束时间
    public static Date getEndDateOfToday() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    //某天的结束时间---获取昨天的结束时间
    public static Date getEndDateOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getEndDateOfToday());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    //某天的开始时间---获取当天的开始时间
    public static String getBeginStringOfToday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time_string = df.format(getBeginDateOfToday());
        return time_string;
    }

    //某天的结束时间---获取当天的结束时间
    public static String getEndStringOfToday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time_string = df.format(getEndDateOfToday());
        return time_string;
    }

    //某天的开始时间---获取昨天的开始时间
    public static String getBeginStringOfYesterday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DATE, -1);
        String time_string = df.format(cal.getTime());
        return time_string;
    }

    //某天的结束时间---获取昨天的结束时间
    public static String getEndStringOfYesterday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.setTime(getEndDateOfToday());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String time_string = df.format(cal.getTime());
        return time_string;
    }

    //获取当前时间的前3天的日期
    public static String getLatelyStringOfThree() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DATE, -2);
        String time_string = df.format(cal.getTime());
        return time_string;
    }

    //获取当前时间的前7天的日期
    public static String getLatelyStringOfSeven() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DATE, -6);
        String time_string = df.format(cal.getTime());
        return time_string;
    }

    //获取当前时间的前15天的日期
    public static String getLatelyStringOf15() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DATE, -14);
        String time_string = df.format(cal.getTime());
        return time_string;
    }

    // 获得本月第一天开始时间
    public static String getStringBeginDayOfCurrentMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        return day_first;
    }

    //获当前时间的前35天的日期
    public static String getLatelyStringOf35() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DATE, -34);
        String time_string = df.format(cal.getTime());
        return time_string;
    }

    //获取当前时间的前7天的日期
    public static Date getLatelyDateOfSeven() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DAY_OF_MONTH, -6);
        return cal.getTime();
    }
    
    //获取当前时间的前3天的日期
    public static Date getLatelyDateOfThree() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DAY_OF_MONTH, -2);
        return cal.getTime();
    }
    
    //获取当前时间的前15天的日期
    public static Date getLatelyDateOf15() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DAY_OF_MONTH, -14);
        return cal.getTime();
    }
    
    //获取当前时间的前30天的日期
    public static Date getLatelyDateOf30() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginDateOfToday());
        cal.add(Calendar.DAY_OF_MONTH, -29);
        return cal.getTime();
    }
    
    //获取当月第一天的日期
    public static Date getLatelyDateOfThisMonth() {
        Date date = TimeUtils.getBeginDateOfToday();
        GregorianCalendar gregorianCalendar = (GregorianCalendar) Calendar.getInstance();
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }
}
