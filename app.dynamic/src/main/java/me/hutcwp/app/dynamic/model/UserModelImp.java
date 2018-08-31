package me.hutcwp.app.dynamic.model;

import android.text.TextUtils;

import java.util.List;

import me.hutcwp.app.dynamic.bean.User;
import me.hutcwp.app.dynamic.database.DBDao;
import me.hutcwp.app.dynamic.interfaze.IUserModel;
import me.hutcwp.app.dynamic.util.LogUtil;
import me.hutcwp.app.dynamic.util.UserUtil;

/**
 * Created by Administrator on 2018/1/15.
 * 登录注册的数据操作接口实现类
 */

public class UserModelImp implements IUserModel {

    private static UserModelImp mInstance;

    private UserModelImp() {
    }

    public static synchronized UserModelImp getInstance() {
        if (mInstance == null) {
            mInstance = new UserModelImp();
        }
        return mInstance;
    }


    @Override
    public boolean login(String phone, String password) {
        List<User> userList = DBDao.getInstance().findAll(User.class);
        if (userList == null) {
            return false;
        }
        for (User user : userList) {
            if (TextUtils.equals(user.getPhone(), phone)
                    && TextUtils.equals(user.getPassword(), password)) {
                UserUtil.setCurUser(new User(user.getId(), user.getPhone(), user.getUsername(), user.getPassword()));
                LogUtil.D("登录的时候初始化curUser");
                LogUtil.D("test", "id:" + user.getId() + "phone:" + user.getPhone() + "password:" + user.getPassword());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean register(User user) {
        return judge(DBDao.getInstance().insert(user));
    }

    @Override
    public User getUserById(int userId) {
        //查询到的是一个结果集合，但是因为查询条件是主键id，所以正常情况下不可能出现size大于1的情况
        List<User> userList = DBDao.getInstance().findAll(User.class, "id=?", new String[]{"" + userId});
        if (userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    /**
     * 判断操作是否成功
     *
     * @return 结果
     */
    private boolean judge(long resultCode) {
        if (resultCode > 0) {
            return true;
        }
        return false;
    }

}
