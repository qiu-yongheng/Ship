package com.kc.shiptransport.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/17  14:34
 * @desc ${TODD}
 */

public class CalendarUtil {
    private static Calendar calendar = Calendar.getInstance();

    private void getWeekAndDay() {
        //获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
            week = week - 1;
        } else {
            day = day - 1;
        }
        System.out.println("今天是本月的第" + week + "周" + ",星期" + (day));
    }

    /**
     * 获取当前日期
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return df.format(cal.getTime());
    }

    /**
     * @return
     */
    public static List<String> getdateOfWeek(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //获取本周一的日期

        List<String> list = new ArrayList<>();
        String date = df.format(cal.getTime());
        Integer sunday = Integer.valueOf(date);
        for (int i = 0; i < 7; i++) {
            list.add(i, String.valueOf(sunday++));
        }

        return list;

        //        System.out.println(df.format(cal.getTime()));
        //        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        //        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //        System.out.println(df.format(cal.getTime()));
        //        //增加一个星期，才是我们中国人理解的本周日的日期
        //        cal.add(Calendar.WEEK_OF_YEAR, 1);
        //        System.out.println(df.format(cal.getTime()));
    }

    public static String[] getdate(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //获取本周一的日期

        String[] list = new String[7];
        for (int i = 0; i < 7; i++) {
            String date = df.format(cal.getTime());
            list[i] = date;
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        return list;
    }

    /**
     * 转换字符串格式
     * @param format
     * @param date
     * @return
     */
    public static String dateFormat(String format, String date) {
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        try {
            Date parse = sdf.parse(date);
            return sdf.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 把日期, itemID转换成list position
     * @param format
     * @param d
     * @return
     */
    public static int dateToPosition(String format, String d) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String date = df.format(cal.getTime());
        Integer sunday = Integer.valueOf(date);
        Integer selectDay = Integer.valueOf(dateFormat(format, d));
        return selectDay - sunday;
    }

    public static String getDate(String format, int value) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        cal.set(Calendar.DAY_OF_WEEK, value);
        return df.format(cal.getTime());
    }


    //获取当前时间(精确到天)且判断季节
    private void getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        System.out.println("date=" + date);
        String dates[] = date.split("-");
        System.out.println("dates[0]=" + dates[0]);
        System.out.println("dates[1]=" + dates[1]);
        System.out.println("dates[2]=" + dates[2]);

        System.out.println("---------------------");

        System.out.println("dates[0]=" + Integer.valueOf(dates[0]));
        System.out.println("dates[1]=" + Integer.valueOf(dates[1]));
        System.out.println("dates[2]=" + Integer.valueOf(dates[2]));

        int month = Integer.valueOf(dates[1]);
        String season = null;
        if (month == 12 || month == 1 || month == 2) {
            season = "冬天";
        } else if (month == 3 || month == 4 || month == 5) {
            season = "春天";
        } else if (month == 6 || month == 7 || month == 8) {
            season = "夏天";
        } else {
            season = "秋天";
        }

        System.out.println("季节:" + season);
    }


    public static void main(String[] args) throws ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = sdf.parse("2011-12-26");
        Date date2 = sdf.parse("2011-06-08");

        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();

        cal1.setTime(date1);
        cal2.setTime(date2);

        long gap = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);//从间隔毫秒变成间隔天数</strong>

        System.out.println("相差"+gap+"天");
    }

    public static long getDataBetween(String sunday, String currentday) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = sdf.parse(sunday);
        Date date2 = sdf.parse(currentday);

        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();

        cal1.setTime(date1);
        cal2.setTime(date2);

        long gap = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);//从间隔毫秒变成间隔天数
        return gap;
    }
}
