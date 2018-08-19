package com.hutcwp.livebiz.base.util;


import android.util.Log;

public class MLog {

    private static boolean isDebug = true;

    public static void ver(String tag, String format) {
        if (isDebug) {
            Log.v(tag, format);
        }
    }

    public static void debug(String tag, String format) {
        if (isDebug) {
            Log.d(tag, format);
        }
    }

    public static void info(String tag, String format) {
        if (isDebug) {
            Log.i(tag, format);
        }
    }

    public static void warrn(String tag, String format) {
        if (isDebug) {
            Log.w(tag, format);
        }
    }

    public static void error(String tag, String format) {
        if (isDebug) {
            Log.e(tag, format);
        }
    }

}
