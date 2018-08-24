package com.hutcwp.main.util;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    // 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    private static long TEN_MINUTES = 600000L;    //10分钟

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @return
     */
    public static java.util.Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = getSimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
        }
        return d;
    }


    /**
     * 把日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(java.util.Date date, String format) {
        String result = "";
        SimpleDateFormat formater = getSimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // log.error(e);
        }
        return result;
    }

    /**
     * 两个日期相减
     *
     * @param firstTime
     * @param secTime
     * @return 相减得到的秒数
     */
    public static long timeSub(String firstTime, String secTime) {

        if (firstTime == null || firstTime.equals("")) {
            //MLog.info("wwd","DataUtils timeSub firstTime is error!");
            return 0;
        }

        if (secTime == null || secTime.equals("")) {
            //MLog.info("wwd","DataUtils timeSub secTime is error!");
            return 0;
        }

        long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
        long second = stringtoDate(secTime, FORMAT_ONE).getTime();
        return (first - second) / 1000;
    }

    /**
     * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getNow() {
        Calendar today = Calendar.getInstance();
        return dateToString(today.getTime(), FORMAT_ONE);
    }


    //预告时间分段
    public static String convertTime(long predictTime) {
        String formatTime;

        Time time = new Time();
        time.set(predictTime);
        int theYear = time.year;
        int theMonth = time.month;
        int theMonthDay = time.monthDay;

        long curTime = System.currentTimeMillis();
        time.set(curTime);

        if ((theYear == time.year)
                && (theMonth == time.month)) {
            if (theMonthDay == time.monthDay) {
                if (predictTime - curTime < TEN_MINUTES) {
                    formatTime = "即将开始";
                } else {
                    formatTime = "今天  " + DateUtils.dateToString(new Date(predictTime), "HH:mm");
                }
            } else if (theMonthDay == time.monthDay + 1) {
                formatTime = "明天  " + DateUtils.dateToString(new Date(predictTime), "HH:mm");
            } else {
                formatTime = DateUtils.dateToString(new Date(predictTime), "M月d日 HH:mm");
            }
        } else {
            formatTime = DateUtils.dateToString(new Date(predictTime), "M月d日 HH:mm");
        }

        return formatTime;
    }

    public static boolean isTomorrow(Calendar cal, long preTime) {
        //今天时间加一天等于明天
        Calendar mintianday = Calendar.getInstance();
        mintianday.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        mintianday.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        mintianday.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);

        Calendar pre = Calendar.getInstance();
        pre.setTime(new Date(preTime));
        if (pre.get(Calendar.DATE) != mintianday.get(Calendar.DATE)) return false;
        if (pre.get(Calendar.MONTH) != mintianday.get(Calendar.MONTH)) return false;
        if (pre.get(Calendar.YEAR) != mintianday.get(Calendar.YEAR)) return false;
        return true;
    }

    //预告时间是否为今天
    public static boolean isToday(Calendar cal, long preTime) {
        Calendar pre = Calendar.getInstance();
        pre.setTime(new Date(preTime));

        if (pre.get(Calendar.DATE) != cal.get(Calendar.DATE)) return false;
        if (pre.get(Calendar.MONTH) != cal.get(Calendar.MONTH)) return false;
        if (pre.get(Calendar.YEAR) != cal.get(Calendar.YEAR)) return false;
        return true;
    }

    public static boolean isYesterday(Calendar cal, long preTime) {
        //今天时间减一天等于昨天
        Calendar mintianday = Calendar.getInstance();
        mintianday.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        mintianday.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        mintianday.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);

        Calendar pre = Calendar.getInstance();
        pre.setTime(new Date(preTime));
        if (pre.get(Calendar.DATE) != mintianday.get(Calendar.DATE)) return false;
        if (pre.get(Calendar.MONTH) != mintianday.get(Calendar.MONTH)) return false;
        if (pre.get(Calendar.YEAR) != mintianday.get(Calendar.YEAR)) return false;
        return true;
    }

    public static SimpleDateFormat getSimpleDateFormat(String format) {
        return CommonUtils.getSimpleDateFormat(format);
    }

    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond + "毫秒");
        }
        return sb.toString();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int getDaysByTwoMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 通过时间秒毫秒数判断是否超过一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean getIsMoreDayByTwoMillisecond(Long date1,Long date2)
    {
        long sum = 1000 * 3600 * 24;
        if (date2 - date1 > sum) {
            return true;
        } else {
            return false;
        }

    }

    public static String getImMsgFormatTime(long msgTime) {
        Calendar c = Calendar.getInstance();
        c.set(c.HOUR_OF_DAY, 23);
        c.set(c.MINUTE, 59);
        c.set(c.SECOND, 59);

        int y1 = c.get(Calendar.YEAR);
        //int m1 = c.get(Calendar.MONTH);
        //int d1 = c.get(Calendar.DAY_OF_MONTH);
        Date today = c.getTime();
        long diff = today.getTime() - msgTime;
        if (diff < 0) diff = 0;
        long days = diff / (1000 * 60 * 60 * 24);

        c.clear();
        c.setTimeInMillis(msgTime);
        int y2 = c.get(Calendar.YEAR);
        //int m2 = c.get(Calendar.MONTH);
        //int d2 = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat format;
        if (days == 0) {
            //当天
            format = CommonUtils.getSimpleDateFormat("HH:mm");
        } else if (y1 != y2) {
            //非当年
            format = CommonUtils.getSimpleDateFormat("yyyy年M月d日");
        } else {
            //当年
            format = CommonUtils.getSimpleDateFormat("M月d日");
        }

        return format.format(c.getTime());
    }

    public static String getGreetMsgFormatTime(long msgTime) {
        Calendar c = Calendar.getInstance();
        c.set(c.HOUR_OF_DAY, 23);
        c.set(c.MINUTE, 59);
        c.set(c.SECOND, 59);

        int y1 = c.get(Calendar.YEAR);
        //int m1 = c.get(Calendar.MONTH);
        //int d1 = c.get(Calendar.DAY_OF_MONTH);
        Date today = c.getTime();
        long diff = today.getTime() - msgTime;
        if (diff < 0) diff = 0;
        long days = diff / (1000 * 60 * 60 * 24);

        c.clear();
        c.setTimeInMillis(msgTime);
        int y2 = c.get(Calendar.YEAR);
        //int m2 = c.get(Calendar.MONTH);
        //int d2 = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat format;
        if (days == 0) {
            //当天
            format = CommonUtils.getSimpleDateFormat("H:mm");
        } else if (y1 != y2) {
            //非当年
            format = CommonUtils.getSimpleDateFormat("yyyy-M-dd");
        } else {
            //当年
            format = CommonUtils.getSimpleDateFormat("M-dd");
        }

        return format.format(c.getTime());
    }

}
