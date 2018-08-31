package me.hutcwp.app.dynamic.interfaze;

import me.hutcwp.app.dynamic.bean.User;

/**
 * Created by Administrator on 2018/1/15.
 */

public interface IUserModel {

    boolean login(String phone, String password);

    boolean register(User user);

    User getUserById(int userId);
}
