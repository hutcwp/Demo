package me.hutcwp.demo;

import android.app.Application;

import me.hutcwp.demo.base.util.BasicConfig;

public class mApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BasicConfig.getInstance().setAppContext(this);
    }

}
