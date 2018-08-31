package me.hutcwp.app.dynamic.util;

/**
 * Created by Administrator on 2018/1/15.
 */

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.hutcwp.app.dynamic.bean.User;
import me.hutcwp.app.dynamic.model.UserModelImp;

/**
 * 公共工具类
 */
public class CommentUtil {

    /**
     * 获取系统当前时间
     *
     * @return 系统当前时间
     */
    public static long getCurTime() {
        return System.currentTimeMillis();
    }

    /**
     * 将字符串格式转换为List集合
     *
     * @param imgStr 字符串
     * @return 图片地址集合
     */
    public static List<String> string2strList(String imgStr) {
        if (imgStr == null) {
            return null;
        }
        String[] res = imgStr.split(",");
        return Arrays.asList(res);
    }


    /**
     * 根据likes中的id列表获取到用户集合
     *
     * @param idstr id列表
     * @return 用户集合
     */
    public static List<User> string2userList(String idstr) {
        if (TextUtils.isEmpty(idstr)) {
            return null;
        }
        List<User> userList = new ArrayList<>();
        String[] ids = idstr.split(",");
        for (String id : ids) {
            User user = UserModelImp.getInstance().getUserById(Integer.valueOf(id));
            userList.add(user);
        }
        return userList;
    }


}
