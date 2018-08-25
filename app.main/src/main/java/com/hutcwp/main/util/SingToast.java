package com.hutcwp.main.util;

import android.widget.Toast;

public class SingToast {

    public static void toast(String msg) {
        Toast.makeText(BasicConfig.getInstance().getAppContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void checkNetToast() {
        if (!CommonUtil.isNetworkAvalible(BasicConfig.getInstance().getAppContext())) {
            Toast.makeText(BasicConfig.getInstance().getAppContext(), "网络连接失败，请检查网络！", Toast.LENGTH_LONG).show();
        }
    }

}
