package com.kc.shiptransport.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bigkoo.pickerview.TimePickerView;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;

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
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static TimePickerView pvTime;

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
     * 获取当前日期, 偏移后的日期
     * @param format
     * @param dateType
     * @param num
     * @return
     */
    public static String getOffsetDate(String format, int dateType, int num) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(dateType, num);

        return df.format(cal.getTime());
    }

    /**
     * 获取当天日期
     *
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
     *
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
     *
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
     *
     * @param jumpWeek
     * @return
     */
    public static int getWeekOfYearNum(int jumpWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, jumpWeek);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前月份
     *
     * @param jumpWeek
     * @return
     */
    public static String getMonthOfYear(int jumpWeek) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月");
        cal.add(Calendar.WEEK_OF_YEAR, jumpWeek);
        return sDateFormat.format(cal.getTime());
    }

    /**
     * 获取一周的日期 (数组)
     *
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
     *
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

        long gap = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24);//从间隔毫秒变成间隔天数
        return gap;
    }

    /**
     * 判断结束日期是否在开始日期之前
     *
     * @param startTime 开始时间
     * @param endTime 结束
     * @return 结束时间小于开始时间, 返回true
     * @throws ParseException
     */
    public static boolean isLastDate(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM);

        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);

        // 公历
        GregorianCalendar startCal = new GregorianCalendar();
        GregorianCalendar endCal = new GregorianCalendar();

        startCal.setTime(start);
        endCal.setTime(end);


        long gap = endCal.getTimeInMillis() - startCal.getTimeInMillis();

        return gap <= 0;
    }

    /**
     * 判断结束日期是否在开始日期之前
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static boolean isLastDate_YYYY_MM_DD(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);

        Date start = sdf.parse(startTime);
        Date end = sdf.parse(endTime);

        // 公历
        GregorianCalendar startCal = new GregorianCalendar();
        GregorianCalendar endCal = new GregorianCalendar();

        startCal.setTime(start);
        endCal.setTime(end);


        long gap = endCal.getTimeInMillis() - startCal.getTimeInMillis();

        return gap <= 0;
    }

    /**
     * 弹出日期 时间选择器
     * DateFormat.is24HourFormat(context) 判断
     *
     * @param context
     * @param view
     */
    public static void showTimePickerDialog(final Context context, final TextView view, boolean isSystem) throws ParseException {
        showTimePickerDialog(context, view, new OnTimePickerSureClickListener() {
            @Override
            public void onSure(String str) {

            }
        }, isSystem);
    }

    /**
     * 弹出日期 时间选择器
     * DateFormat.is24HourFormat(context) 判断
     *
     * @param context
     * @param view
     */
    public static void showTimePickerDialog(final Context context, final TextView view, final OnTimePickerSureClickListener listener, boolean isSystem) throws ParseException {
        if (isSystem) {
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
                            listener.onSure(format);
                        }
                    }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

                    timePickerDialog.show();


                }
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        } else {
            showTimePicker(context, view, YYYY_MM_DD_HH_MM, null, listener, new boolean[]{true, true, true, true, true, false}, false);
        }

    }

    /**
     * 弹出日期时间选择器
     *
     * @param context
     * @param listener
     */
    public static void showTimePickerDialog(final Context context, final OnTimePickerSureClickListener listener, boolean isSystem) throws ParseException {
        if (isSystem) {
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
                            listener.onSure(format);
                        }
                    }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

                    timePickerDialog.show();


                }
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        } else {
            showTimePicker(context, null, YYYY_MM_DD_HH_MM, null, listener, new boolean[]{true, true, true, true, true, false}, false);
        }
    }

    /**
     * 弹出日期选择器
     *
     * @param context
     * @param view
     */
    public static void showDatePickerDialog(final Context context, final TextView view, boolean isSystem) throws ParseException {
        showDatePickerDialog(context, view, new OnTimePickerSureClickListener() {
            @Override
            public void onSure(String str) {

            }
        }, false);
    }

    /**
     * 弹出日期选择器
     *
     * @param context
     * @param view
     */
    public static void showDatePickerDialog(final Context context, final TextView view, final OnTimePickerSureClickListener listener, boolean isSystem) throws ParseException {
        //        if (isSystem) {
        //            final Calendar now = Calendar.getInstance();
        //
        //            // 显示日期选择器
        //            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
        //                @Override
        //                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //                    final Calendar c = Calendar.getInstance();
        //                    c.set(Calendar.YEAR, year);
        //                    c.set(Calendar.MONTH, month);
        //                    c.set(Calendar.DAY_OF_MONTH, day);
        //
        //                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //                    String format = df.format(c.getTime());
        //                    view.setText(format);
        //                    listener.onSure(format);
        //                }
        //            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        //
        //
        //            datePickerDialog.show();
        //        } else {
        //            showTimePicker(context, view, YYYY_MM_DD, null, listener, new boolean[]{true, true, true, false, false, false}, false);
        //        }

        showDatePickerDialog(context, view, listener, false, isSystem);
    }

    /**
     * 弹出日期选择器
     *
     * @param context
     * @param view
     * @param listener
     * @param isDialog
     * @param isSystem
     * @throws ParseException
     */
    public static void showDatePickerDialog(final Context context, final TextView view, final OnTimePickerSureClickListener listener, boolean isDialog, boolean isSystem) throws ParseException {
        if (isSystem) {
            final Calendar now = Calendar.getInstance();

            // 显示日期选择器
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    final Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month);
                    c.set(Calendar.DAY_OF_MONTH, day);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String format = df.format(c.getTime());
                    view.setText(format);
                    listener.onSure(format);
                }
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));


            datePickerDialog.show();
        } else {
            showTimePicker(context, view, YYYY_MM_DD, null, listener, new boolean[]{true, true, true, false, false, false}, isDialog);
        }
    }

    /**
     * 弹出时间选择器
     * DateFormat.is24HourFormat(context) 判断
     *
     * @param context
     * @param view
     */
    public static void showTimeDialog(final Context context, final TextView view, final String format, String Date, final OnTimePickerSureClickListener listener, boolean isSystem) throws ParseException {
        if (isSystem) {
            Calendar calendar = getCalendar(Date);

            // 只修改当前的日期, 时间不修改
            final Calendar now = Calendar.getInstance();
            // 设置日
            now.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));

            // 显示时间选择器
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    now.set(Calendar.HOUR_OF_DAY, hour);
                    now.set(Calendar.MINUTE, minute);
                    SimpleDateFormat df = new SimpleDateFormat(format);
                    String format = df.format(now.getTime());
                    view.setText(format);
                    listener.onSure(format);
                }
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

            timePickerDialog.show();
        } else {
            showTimePicker(context, view, format, Date, listener, new boolean[]{false, false, false, true, true, false}, false);
        }
    }

    /**
     * 选择指定日期的时间
     *
     * @param context
     * @param view
     * @param format
     * @param Date
     * @param listener
     * @throws ParseException
     */
    public static void showDateDialog(final Context context, final TextView view, final String format, String Date, final OnTimePickerSureClickListener listener, boolean isSystem) throws ParseException {
        if (isSystem) {
            Calendar calendar = getCalendar(Date);

            // 只修改当前的日期, 时间不修改
            final Calendar now = Calendar.getInstance();
            now.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));

            // 显示时间选择器
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    now.set(Calendar.HOUR_OF_DAY, hour);
                    now.set(Calendar.MINUTE, minute);
                    SimpleDateFormat df = new SimpleDateFormat(format);
                    String format = df.format(now.getTime());
                    view.setText(format);
                    listener.onSure(format);
                }
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);

            timePickerDialog.show();
        } else {
            showTimePicker(context, view, format, Date, listener, new boolean[]{true, true, true, false, false, false}, false);
        }

    }

    /**
     * 弹出时间选择器
     *
     * @param context  上下文
     * @param view     要赋值的控件
     * @param format   时间格式
     * @param Date     指定日期
     * @param listener 选择后的回调监听
     * @param type     显示数据类型
     * @param isDialog 是否以弹窗样式显示
     */
    public static void showTimePicker(Context context, final View view, final String format, String Date, final OnTimePickerSureClickListener listener, boolean[] type, boolean isDialog) throws ParseException {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

        Calendar selectedDate = Calendar.getInstance();
        // 如果有指定日期, 设置当前默认选中时间为指定时间
        if (!TextUtils.isEmpty(Date)) {
            Calendar now = getCalendar(Date);
            // 设置年
            selectedDate.set(Calendar.YEAR, now.get(Calendar.YEAR));
            // 设置月
            selectedDate.set(Calendar.MONTH, now.get(Calendar.MONTH));
            // 设置日
            selectedDate.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
        }


        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);

        //时间选择器
        pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                String time = getTime(date, format);

                if (v != null) {
                    TextView textView = (TextView) v;
                    textView.setText(time);
                }
                listener.onSure(time);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(type)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .isDialog(isDialog)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

        pvTime.show(view);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param formatType
     * @return
     */
    private static String getTime(Date date, String formatType) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.format(date);
    }


    /**
     * 根据传入的时间, 获取calendar
     *
     * @param Date
     * @return
     * @throws ParseException
     */
    @NonNull
    private static Calendar getCalendar(String Date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        java.util.Date d = sdf.parse(Date);
        // 公历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar;
    }
}
