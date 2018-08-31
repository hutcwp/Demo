package me.hutcwp.app.dynamic.ui.login;

import me.hutcwp.app.dynamic.model.UserModelImp;
import me.hutcwp.app.dynamic.mvp.BasePresent;

/**
 * Created by Administrator on 2018/1/19.
 */

public class LoginPresent extends BasePresent {

    private ILoginView loginView;


    public LoginPresent(ILoginView loginView) {
        this.loginView = loginView;
    }

    void login() {
        if (UserModelImp.getInstance().login(loginView.getPhone(), loginView.getPassword())) {
            loginView.showSuccess();
        } else {
            loginView.showFailed();
        }
    }

}
