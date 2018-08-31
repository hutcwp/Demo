package me.hutcwp.app.dynamic.ui.login;

import me.hutcwp.app.dynamic.mvp.IBaseView;

/**
 * Created by Administrator on 2018/1/19.
 */

public interface ILoginView extends IBaseView {

    String getPhone();

    String getPassword();

    void showSuccess();

    void showFailed();

}
