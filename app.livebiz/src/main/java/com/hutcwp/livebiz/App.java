package com.hutcwp.livebiz;

import android.app.Application;

import com.hutcwp.livebiz.base.util.BasicConfig;

/**
 * Created by hutcwp on 2018/8/19 23:37
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BasicConfig.getInstance().setAppContext(this);
    }
}
