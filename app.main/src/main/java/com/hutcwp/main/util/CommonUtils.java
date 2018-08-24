package com.hutcwp.main.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonUtils {  private static long lastClickTime;
    public static boolean isCurrentMainThread() {
        return Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId();
    }

    public static boolean isFastClick(long timeThreshold) {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < timeThreshold) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void setGLSurfaceViewThreadName(String name) {
        if (name == null) {
            return;
        }

        try {
            Thread currentThread = Thread.currentThread();
            String threadName = currentThread.getName();
            if (threadName != null && threadName.startsWith("GLThread ")) {
                currentThread.setName(name);
            }
        } catch (Throwable e) {
            Log.e("CommonUtils", "Empty Catch on setGLSurfaceViewThreadName", e);
        }
    }


    private static HashMap<String, SimpleDateFormat> mSimpleDateFormatCache = new HashMap<String, SimpleDateFormat>();
    public static SimpleDateFormat getSimpleDateFormat(String format) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            SimpleDateFormat sdf = mSimpleDateFormatCache.get(format);
            if (sdf == null) {
                sdf = new SimpleDateFormat(format);
                mSimpleDateFormatCache.put(format, sdf);
            }

            return sdf;
        } else {
            return new SimpleDateFormat(format);
        }
    }

    //注意：该方法是IPC操作，可能会耗时；请在子线程调用，且尽量避免频繁调用
    //返回结果：单位KB
    public static int getAvailMemory() {
        int availMem = 0;
        try {
            ActivityManager.MemoryInfo mem = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) BasicConfig.getInstance().getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mem);
            availMem = (int)(mem.availMem / 1024);
        } catch (Throwable e) {
            Log.e("CommonUtils", "getTotalMemory()" + e.toString());
        }

        return availMem;
    }

    //注意：该方法是IPC操作，可能会耗时；请在子线程调用，且尽量避免频繁调用
    //返回结果：单位M
    public static int getTotalMemory() {
        int availMem = -1;
        try {
            ActivityManager.MemoryInfo mem = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) BasicConfig.getInstance().getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mem);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                availMem = 1024;
            } else {
                availMem = (int)(mem.totalMem >>> 20);// convert to M bytes
            }
        } catch (Throwable e) {
            Log.e("CommonUtils", "getTotalMemory()" + e.toString());
        }

        return availMem;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        if(context==null){
            return false;
        }

        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }

        return pName.contains(packageName);
    }
}
