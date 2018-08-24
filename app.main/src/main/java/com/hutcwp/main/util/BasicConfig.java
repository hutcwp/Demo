package com.hutcwp.main.util;

import android.content.Context;

public class BasicConfig {

    private Context mContext;
    private static BasicConfig mInstance = new BasicConfig();

    public void setAppContext(Context context) {
        mContext = context;
    }

    public static BasicConfig getInstance() {
        return mInstance;
    }


    public Context getAppContext() {
        return mContext;
    }
}
