package me.hutcwp.app.dynamic.ui.register;

import me.hutcwp.app.dynamic.model.UserModelImp;
import me.hutcwp.app.dynamic.mvp.BasePresent;

/**
 * Created by Administrator on 2018/1/19.
 */

public class RegisterPresent extends BasePresent {

    private IRegisterView registerView;

    public RegisterPresent(IRegisterView registerView) {
        this.registerView = registerView;
    }

    void register() {
        if (registerView.getRegisterUser()!=null) {
            if (UserModelImp.getInstance().register(registerView.getRegisterUser())){
                registerView.success();
            }
        }
    }

}
