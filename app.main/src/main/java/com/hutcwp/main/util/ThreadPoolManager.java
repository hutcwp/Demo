package com.hutcwp.main.util;

import android.annotation.SuppressLint;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private ThreadPoolManager() {
    }

    // 饿汉式 线程安全
    private static ThreadPoolManager instance = new ThreadPoolManager();
    private PoolProxy longPoolProxy; //  给联网使用的线程池
    private PoolProxy shortPoolProxy; // 读写文件使用的线程池

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    // 联网
    // 读写文件
    // 效率最高 cpu 核心数 *2+1
    // 1 *2+1
    public PoolProxy createLongThreadPool() {
        if (longPoolProxy == null) {
            longPoolProxy = new PoolProxy(5, 5, 5000);
        }
        return longPoolProxy;
    }


    public PoolProxy createShortThreadPool() {
        if (shortPoolProxy == null) {
            shortPoolProxy = new PoolProxy(3, 3, 5000);
        }
        return shortPoolProxy;
    }


    /**
     * 配置线程池 代理类 ThreadPoolExecutor 线程池
     *
     * @author wxj
     */
    public class PoolProxy {
        private int corePoolSize;
        private int maximumPoolSize;
        private long time;
        private ThreadPoolExecutor threadPool; // 线程池

        public PoolProxy(int corePoolSize, int maximumPoolSize, long time) {
            super();
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.time = time;
        }

        @SuppressLint("NewApi")
        public void execute(Runnable r) {
            /**
             * 1.corePoolSize 初始化线程数量 2.maximumPoolSize 出了初始化 线程数量 另外最多 能创建的线程数量
             * 3.keepAliveTime 如果没有任务 最多的存活时间 4. TimeUnit时间单位
             */
            if (threadPool == null) { // 当线程池 为空的时候才去创建
                threadPool = new ThreadPoolExecutor(corePoolSize,
                        maximumPoolSize, time, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<Runnable>(10));
            }
            // 就直接执行任务
            threadPool.execute(r);
        }

        /**
         * 注意在需要取消任务的时候，不要使用shutdown(),或者shutdownNow();
         * 来终止当前任务，因为你终止了当前任务，那样当前线程池还是存在的，
         * 再向线程池提交任务的时候，可能会因为队列已满，出现RejectedExecutionException
         * <p>
         * 所以使用remove方法移除当前任务来达到取消任务的效果，
         * <p>
         * 获取时线程队列使用getQueue()方法
         */
        public void cancel() {
            // threadPool 不能为空 threadPool 没有崩溃 threadPool 没有停止
            if (threadPool != null && !threadPool.isShutdown()
                    && !threadPool.isTerminated()) {
                BlockingQueue<Runnable> queue = threadPool.getQueue();
                for (Runnable r : queue) {
                    threadPool.remove(r);
                }
            }
        }
    }
}
