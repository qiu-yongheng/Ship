package com.kc.shiptransport.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

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
     * 获取当天日期
     * @param format 自定义格式化格式
     * @return
     */
    public static String getCurrentDate(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(cal.getTime());
    }

    /**
     * 获取指定星期的日期
     * @param format
     * @param value
     * @return
     */
    public static String getSelectDate(String format, int value, int jumpWeek) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        cal.set(Calendar.DAY_OF_WEEK, value);

        cal.add(Calendar.WEEK_OF_YEAR, jumpWeek);
        return df.format(cal.getTime());
    }

    /**
     * 获取一周的日期 (集合)
     * ++: 获取下个星期的日期
     * --: 获取上个星期的日期
     * @param format
     * @return
     */
    public static List<String> getdateOfWeek(String format, int jumpWeek) {
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        cal.add(Calendar.WEEK_OF_YEAR, jumpWeek);

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //获取本周一的日期
        list.add(df.format(cal.getTime()));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        list.add(df.format(cal.getTime()));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        list.add(df.format(cal.getTime()));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        list.add(df.format(cal.getTime()));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        list.add(df.format(cal.getTime()));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        list.add(df.format(cal.getTime()));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        list.add(df.format(cal.getTime()));

        return list;
    }

    /**
     * 获取当前是一年中的第几周
     * @param jumpWeek
     * @return
     */
    public static int getWeekOfYearNum (int jumpWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, jumpWeek);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前月份
     * @param jumpWeek
     * @return
     */
    public static String getMonthOfYear (int jumpWeek) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月");
        cal.add(Calendar.WEEK_OF_YEAR, jumpWeek);
        return sDateFormat.format(cal.getTime());
    }

    /**
     * 获取一周的日期 (数组)
     * @param format
     * @return
     */
    public static String[] getdateToWeek(String format) {
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
     * 获取当前时间(精确到天)且判断季节
     */
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

    /**
     * 计算日期之间的时间差
     * @param sunday
     * @param currentday
     * @return
     * @throws ParseException
     */
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

    /**
     * 弹出时间选择器
     * DateFormat.is24HourFormat(context) 判断
     * @param context
     * @param view
     */
    public static void showTimePickerDialog(final Context context, final TextView view) {
        final Calendar now = Calendar.getInstance();

        // 显示日期选择器
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, day);

                // 显示时间选择器
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        c.set(Calendar.HOUR_OF_DAY, hour);
                        c.set(Calendar.MINUTE, minute);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String format = df.format(c.getTime());
                        view.setText(format);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

                timePickerDialog.show();


            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }
}
