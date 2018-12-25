package com.bigwangoo.sample.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 聊天时间格式化
 * Created by YaoDong.Wang on 2017/9/6.
 */
public class ChatTimeUtils {

    static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 时间戳格式转换
     */
    public static String getNewChatTime(long timestamp) {
        String result = "";

        Calendar curCalendar = Calendar.getInstance();
        Calendar showCalendar = Calendar.getInstance();
        showCalendar.setTimeInMillis(timestamp);

        String timeFormat;
        String yearTimeFormat;
        String am_pm = "";
        int hour = showCalendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 6) {
            am_pm = "凌晨";
        } else if (hour >= 6 && hour < 12) {
            am_pm = "早上";
        } else if (hour == 12) {
            am_pm = "中午";
        } else if (hour > 12 && hour < 18) {
            am_pm = "下午";
        } else if (hour >= 18) {
            am_pm = "晚上";
        }
        timeFormat = "M月d日 " + am_pm + "HH:mm";
        yearTimeFormat = "yyyy年M月d日 " + am_pm + "HH:mm";

        boolean yearTemp = curCalendar.get(Calendar.YEAR) == showCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int curMonth = curCalendar.get(Calendar.MONTH);
            int showMonth = showCalendar.get(Calendar.MONTH);
            // 表示是同一个月
            if (curMonth == showMonth) {
                int temp = curCalendar.get(Calendar.DATE) - showCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timestamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timestamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int curDayOfMonth = curCalendar.get(Calendar.WEEK_OF_MONTH);
                        int showDayOfMonth = showCalendar.get(Calendar.WEEK_OF_MONTH);
                        // 表示是同一周
                        if (showDayOfMonth == curDayOfMonth) {
                            int dayOfWeek = showCalendar.get(Calendar.DAY_OF_WEEK);
                            //判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                            if (dayOfWeek != 1) {
                                result = dayNames[showCalendar.get(Calendar.DAY_OF_WEEK) - 1]
                                        + getHourAndMin(timestamp);
                            } else {
                                result = getTime(timestamp, timeFormat);
                            }
                        } else {
                            result = getTime(timestamp, timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timestamp, timeFormat);
                        break;
                }
            } else {
                result = getTime(timestamp, timeFormat);
            }
        } else {
            result = getYearTime(timestamp, yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     *
     * @param time time
     * @return HH:mm
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     *
     * @param time       time
     * @param timeFormat timeFormat
     * @return String
     */
    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     *
     * @param time           time
     * @param yearTimeFormat yearTimeFormat
     * @return String
     */
    public static String getYearTime(long time, String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }

}
