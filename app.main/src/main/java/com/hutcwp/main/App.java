package com.hutcwp.main;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.hutcwp.main.util.BasicConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BasicConfig.getInstance().setAppContext(this);
        AVOSCloud.setDebugLogEnabled(true);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"rV6smcRoQUQLoAMzeUAnaVqN-gzGzoHsz","MfPdfFjrYfhIiG7jGaoH2iDx");
    }
}
