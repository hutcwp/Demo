package me.hutcwp.app.dynamic.ui.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import me.hutcwp.app.dynamic.R;
import me.hutcwp.app.dynamic.base.BaseActivity;
import me.hutcwp.app.dynamic.bean.User;
import me.hutcwp.app.dynamic.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity implements IRegisterView {

    private ActivityRegisterBinding mBinding;

    private RegisterPresent registerPresent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mBinding = (ActivityRegisterBinding) getBinding();
        registerPresent = new RegisterPresent(this);
        mBinding.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        mBinding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPresent.register();
            }
        });
    }


    @Override
    protected void loadData() {

    }


    @Override
    public User getRegisterUser() {
        String phone = mBinding.etPhone.getText().toString().trim();
        String name = mBinding.etUsername.getText().toString().trim();
        String pwd = mBinding.etPassword.getText().toString().trim();
        String pwd2 = mBinding.etPasswordAgain.getText().toString().trim();
        if (isValid(phone, name, pwd, pwd2)) {
            return new User(phone, name, pwd);
        } else {
            return null;
        }
    }

    @Override
    public void success() {
        toast("注册成功");
        this.finish();
    }

    @Override
    public void failed() {
        toast("注册失败");
    }

    /**
     * 合理性判断
     */
    private boolean isValid(String phone, String name, String pwd, String pwd2) {
        //两次输入密码不一致，以及判空
        if (!TextUtils.equals(pwd, pwd2)
                || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(pwd)) {
            toast("输入不合理");
            return false;
        }
        return true;
    }
}
