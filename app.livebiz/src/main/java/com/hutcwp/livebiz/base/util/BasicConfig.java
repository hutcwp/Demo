package com.hutcwp.livebiz.base.util;

import android.content.Context;

public class BasicConfig {

    private boolean isDebuggable;
    private Context mContext;
    private static BasicConfig mInstance = new BasicConfig();

    public static BasicConfig getInstance() {
        return mInstance;
    }

    public void setDebuggable(boolean debuggable) {
        isDebuggable = debuggable;
    }

    public boolean isDebuggable() {
        return isDebuggable;
    }

    public void setAppContext(Context context) {
        mContext = context;
    }

    public Context getAppContext() {
        return mContext;
    }

}
