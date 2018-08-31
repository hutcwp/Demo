package me.hutcwp.app.dynamic.util;

import me.hutcwp.app.dynamic.bean.User;

/**
 * Created by Administrator on 2018/1/15.
 */

public class UserUtil {

    private static User curUser;

    public static User getCurUser() {
        return curUser;
    }

    public static void setCurUser(User user) {
        curUser = user;
    }

}
