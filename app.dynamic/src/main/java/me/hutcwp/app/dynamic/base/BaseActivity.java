package me.hutcwp.app.dynamic.base;

import android.app.Activity;
import android.content.Intent;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import me.hutcwp.app.dynamic.mvp.IBaseView;


/**
 * Created by Administrator on 2018/1/12.
 */

public abstract class BaseActivity extends Activity implements IBaseView {
    protected abstract @LayoutRes
    int getLayoutId();

    protected ViewDataBinding binding;

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void loadData();

    /**
     * 获取ViewDataBinding对象
     *
     * @return ViewDataBinding对象
     */
    public ViewDataBinding getBinding() {
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        super.onCreate(savedInstanceState);
        initViews(savedInstanceState);
        loadData();
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 打开一个Activity 默认 不关闭当前activity
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    /**
     * @param clz                    需要打开的Activity类
     * @param isCloseCurrentActivity 是否需要关闭当前Activity
     */
    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finish();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorLayout(View.OnClickListener listener) {

    }

    @Override
    public void hideErrorLayout() {

    }
}
