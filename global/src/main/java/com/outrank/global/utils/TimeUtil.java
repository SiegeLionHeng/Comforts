package com.outrank.global.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.outrank.global.global.AppGlobal;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Gabriel on 2019/3/27.
 * Email 17600284843@163.com
 * Description: 这是时间管理工具类
 */
public class TimeUtil {
    private static TimeUtil instance = null;
    private SharedPreferences sharedPre;
    private SharedPreferences.Editor editor;
    private long diffTime = 0;

    public static TimeUtil getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return new TimeUtil();
        }
    }

    public TimeUtil() {
        sharedPre = AppGlobal.getInstance().getSharedPreferences("managementsystem", Context.MODE_PRIVATE);
        editor = sharedPre.edit();
        instance = this;
    }

    public long getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(Long serverTime) {
        diffTime = System.currentTimeMillis() / 1000 - serverTime;
        if (diffTime != 0) {
            diffTime = -diffTime;
        }
        editor.putLong("diffTime", diffTime);
        editor.commit();
    }

    public int getVoteTime(String catId) {
        long voteTime = sharedPre.getLong("voteTime" + catId, 0);
        if (voteTime == 0) {
            return 0;
        }
        Long time = System.currentTimeMillis() / 1000;
        if (time > voteTime && time - voteTime < 1800) {
            return 30 - (int) ((time - voteTime) / 60);
        }
        return 0;
    }

    public void setVoteTime(String catId) {
        editor.putLong("voteTime" + catId, System.currentTimeMillis() / 1000);
        editor.commit();
    }

    public long getServerTime() {
        return System.currentTimeMillis() / 1000 + getDiffTime();
    }

    /**
     * 存储更新时间
     */
    public void setUpdateTime(String name) {
        editor.putLong(name, System.currentTimeMillis() / 1000);
        editor.commit();
    }

    public long getUpdateTime(String name) {
        long time = sharedPre.getLong(name, 0);
        return System.currentTimeMillis() / 1000 - time;
    }

    /**
     * 获取数据存储时间
     */
    public long getDataTime(String name) {
        long dataTime = sharedPre.getLong(name, 0);
        if (dataTime == 0) {
            return 0;
        }
        Long time = System.currentTimeMillis() / 1000;
        return time - dataTime;
    }

    public void setDataTime(String name) {
        editor.putLong(name, System.currentTimeMillis() / 1000);
        editor.commit();
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || "null".equals(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }
}