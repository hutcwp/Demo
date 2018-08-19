package me.hutcwp.demo;

import android.app.Application;

import net.wequick.small.Small;

import me.hutcwp.demo.base.util.BasicConfig;

public class mApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BasicConfig.getInstance().setAppContext(this);
        Small.preSetUp(this);
    }

}
