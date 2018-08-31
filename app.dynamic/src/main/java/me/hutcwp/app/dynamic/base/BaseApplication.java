package me.hutcwp.app.dynamic.base;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;


/**
 * Created by Administrator on 2018/1/12.
 */

public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    public static BaseApplication getInstance() {
        return sInstance;
    }

    /**
     * 获取Context
     *
     * @return 返回Context的对象
     */
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
//        LeakCanary.install(this);
    }

}
