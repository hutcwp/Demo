package me.hutcwp.app.dynamic.ui.login;

import android.os.Bundle;
import android.view.View;

import me.hutcwp.app.dynamic.R;
import me.hutcwp.app.dynamic.base.BaseActivity;
import me.hutcwp.app.dynamic.databinding.ActivityLoginBinding;
import me.hutcwp.app.dynamic.model.UserModelImp;
import me.hutcwp.app.dynamic.ui.home.HomeActivity;
import me.hutcwp.app.dynamic.ui.register.RegisterActivity;


public class LoginActivity extends BaseActivity implements ILoginView {

    private ActivityLoginBinding mBinding;

    private LoginPresent loginPresent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        loginPresent = new LoginPresent(this);
        mBinding = (ActivityLoginBinding) getBinding();
        mBinding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(RegisterActivity.class, false);
            }
        });
        mBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresent.login();
            }
        });
    }

    @Override
    protected void loadData() {
        //自动登录
        //执行登录操作
//        if (UserModelImp.getInstance().login("123456", "123456")) {
//            toast("登录成功！");
//            gotoActivity(HomeActivity.class, true);
//        } else {
//            toast("登录失败！");
//        }
    }

    @Override
    public String getPhone() {
        return mBinding.etPhone.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mBinding.etPassword.getText().toString().trim();
    }

    @Override
    public void showSuccess() {
        toast("success");
        gotoActivity(HomeActivity.class, true);
    }

    @Override
    public void showFailed() {
        toast("failed");
    }

}
