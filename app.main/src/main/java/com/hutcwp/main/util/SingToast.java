package com.hutcwp.main.util;

import android.widget.Toast;

public class SingToast {

    public static void toast(String msg) {
        Toast.makeText(BasicConfig.getInstance().getAppContext(), msg, Toast.LENGTH_LONG).show();
    }

}
