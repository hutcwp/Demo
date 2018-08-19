package me.hutcwp.demo;

import android.app.Application;

import net.wequick.small.Small;


public class mApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Small.preSetUp(this);
    }

}
