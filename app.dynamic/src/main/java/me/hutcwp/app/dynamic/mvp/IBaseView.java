package me.hutcwp.app.dynamic.mvp;

import android.view.View;

/**
 * Created by Administrator on 2018/1/19.
 */

public interface IBaseView {
    /**
     * 显示loading框
     */
    void showProgress();

    /**
     * 隐藏loading框
     */
    void hideProgress();

    /**
     * 显示异常布局
     *
     * @param listener
     */
    void showErrorLayout(View.OnClickListener listener);

    void hideErrorLayout();
}
