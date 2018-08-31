package me.hutcwp.app.dynamic.ui.register;

import me.hutcwp.app.dynamic.bean.User;
import me.hutcwp.app.dynamic.mvp.IBaseView;

/**
 * Created by Administrator on 2018/1/19.
 */

public interface IRegisterView extends IBaseView {

    User getRegisterUser();

    void success();

    void failed();

}
