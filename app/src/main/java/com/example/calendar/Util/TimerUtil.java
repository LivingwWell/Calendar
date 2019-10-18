package com.example.calendar.Util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimerUtil {
    private final int MSG_TYPE_START = 0;
    private final int MSG_TYPE_PAUSE = 1;
    private final int MSG_TYPE_STOP = 2;
    private int passedTime ;
    private TimeCallBack timeCallBack;

    public TimerUtil() {
        passedTime = 0;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_TYPE_START:
                    handler.postDelayed(recordTime,1000);
                    break;
                case MSG_TYPE_PAUSE:
                    handler.removeMessages(MSG_TYPE_START);
                    break;
                case MSG_TYPE_STOP:
                    passedTime = 0;
                    if (timeCallBack != null){
                        timeCallBack.onTime(passedTime);
                    }
                    handler.removeCallbacks(recordTime);
                    handler.removeMessages(MSG_TYPE_START);
                    break;
            }
        }
    };

    Runnable recordTime = new Runnable() {
        @Override
        public void run() {
            passedTime++;
            if (timeCallBack != null){
                timeCallBack.onTime(passedTime);
            }
            handler.sendEmptyMessage(MSG_TYPE_START);
        }
    };

    public void startCount(){
        handler.sendEmptyMessage(MSG_TYPE_START);
    }
    public void pauseCount(){
        handler.sendEmptyMessage(MSG_TYPE_PAUSE);
    }
    public void stopCount(){
        handler.sendEmptyMessage(MSG_TYPE_STOP);
//        handler.removeCallbacksAndMessages(null);
    }
    public interface TimeCallBack{
        void onTime(int passedTime);
    }
    public void setTimeCallBack(TimeCallBack timeCallBack){
        this.timeCallBack = timeCallBack;
    }

    public void onDestory(){
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 获取日历事件结束日期
     *
     * @param time time in ms
     */
    private static String getEndDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return format.format(date);
    }

    /**
     * 获取最终日历事件重复规则
     *
     * @param time time in ms
     *
     */
    public static String getFinalRRuleMode(long time) {
        return getEndDate(time) + "T235959Z";
    }

    /**
     * 格式化星期
     */
    private static String formatWeek(int week) {
        switch (week) {
            case 0:
                return "SU";
            case 1:
                return "MO";
            case 2:
                return "TU";
            case 3:
                return "WE";
            case 4:
                return "TH";
            case 5:
                return "FR";
            case 6:
                return "SA";
            default:
                return null;
        }
    }

    /**
     * 获取重复周
     *
     * @param time time in ms
     */
    public static String getWeekForDate(long time) {
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 0;
        }
        return formatWeek(week);
    }

    /**
     * 获取指定时间段在一个月中的哪一天
     *
     * @param time time in ms
     */
    public static int getDayOfMonth(long time) {
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * check null
     */
    public static void checkContextNull(Context context) {
        if (null == context) {
            throw new IllegalArgumentException("context can not be null");
        }
    }

}
