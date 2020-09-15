package com.outrank.global.utils;

import android.util.Log;


/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description: Log打印工具类
 */
public class LogUtils {
    private static String className;//类名
    private static String methodName;//方法名
    private static int lineNumber;//行数
    private static final String prefixFilter = "Log ";
    private static boolean DEBUG = true;

    private LogUtils() {
        /* Protect from instantiations */
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[" + methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")").append("]--  ");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(String message) {
        if (DEBUG) {
            // Throwable instance must be created before any methods
            getMethodNames(new Throwable().getStackTrace());
            Log.e(prefixFilter + className, createLog(message));
        }
    }

    public static void i(String message) {
        if (DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.i(prefixFilter + className, createLog(message));
        }
    }

    public static void d(String message) {
        if (DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.d(prefixFilter + className, createLog(message));
        }
    }

    public static void v(String message) {
        if (DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.v(prefixFilter + className, createLog(message));
        }
    }

    public static void w(String message) {
        if (DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.w(prefixFilter + className, createLog(message));
        }
    }

    public static void wtf(String message) {
        if (DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.wtf(prefixFilter + className, createLog(message));
        }
    }

    private static int LOG_MAXLENGTH = 2000;

    public static void el(String msg) {
        e("LogUtil", msg);
    }

    public static void e(String tagName, String msg) {
        if (DEBUG) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e(tagName + i, createLog(msg.substring(start, strLength)));
                    break;
                }
            }
        }
    }


}
