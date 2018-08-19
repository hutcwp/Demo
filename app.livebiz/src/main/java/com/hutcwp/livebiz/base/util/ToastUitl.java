package com.hutcwp.livebiz.base.util;

import android.widget.Toast;

public class ToastUitl {

    public static void show(String msg) {
        Toast.makeText(BasicConfig.getInstance().getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
