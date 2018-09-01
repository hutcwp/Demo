package com.hutcwp.main.util;

import android.os.Handler;

public class ThreadUtils {
    private static ThreadPoolManager.PoolProxy longThreadPool;
    private static ThreadPoolManager.PoolProxy shortThreadPool;

    /**
     * 子线程执行  给联网 ... 特别耗时的 操作使用
     *
     * @param r
     */
    public static void runOnLongBackThread(Runnable r) {
        longThreadPool = ThreadPoolManager.getInstance().createLongThreadPool();
        longThreadPool.execute(r);
    }

    /**
     * 子线程执行   给相对联网  耗时少的操作使用
     *
     * @param r
     */
    public static void runOnShortBackThread(Runnable r) {
        shortThreadPool = ThreadPoolManager.getInstance().createShortThreadPool();
        shortThreadPool.execute(r);
    }

    /**
     * 取消给联网 ... 特别耗时的任务
     */
    public static void cancelLongBackThread() {
        if (longThreadPool != null) {
            longThreadPool.cancel();
        }
    }

    /**
     * 取消给相对联网  耗时少的操作使用时的任务
     */
    public static void cancelShortThread() {
        if (shortThreadPool != null) {
            shortThreadPool.cancel();
        }
    }

    private static Handler handler = new Handler();

    /**
     * 在主线程执行
     *
     * @param r
     */
    public static void runOnUiThread(Runnable r) {
        handler.post(r);
    }
}
