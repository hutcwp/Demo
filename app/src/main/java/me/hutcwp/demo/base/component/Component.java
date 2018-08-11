package me.hutcwp.demo.base.component;


import android.support.v4.app.FragmentManager;

import me.hutcwp.demo.base.mvp.MvpFragment;
import me.hutcwp.demo.base.mvp.MvpPresenter;
import me.hutcwp.demo.base.mvp.MvpView;
import me.hutcwp.demo.base.util.MLog;

public abstract class Component<P extends MvpPresenter<V>, V extends MvpView> extends MvpFragment<P, V>  {

    private static final String TAG = "Component";

//    abstract int getResId();

    public Component() {
    }


    final public FragmentManager getChildManager() {
        return getChildManager();
    }

    public void showSelf() {
        if (!getFragmentManager().isDestroyed()) {
            getFragmentManager().beginTransaction().show(this).commitAllowingStateLoss();
        }
    }

    public void hideSelf() {
        if (!getFragmentManager().isDestroyed()) {
            getFragmentManager().beginTransaction().hide(this).commitAllowingStateLoss();
        }
    }

    /*
     * 获取当前组件容器activity的上下文，此上下文为activity的一个hashcode，用于区分不同的页面用
     * @return
     */
    public int getActivityContext() {
        int context = 0;
        if (getContext() != null) {
            context = getContext().hashCode();
            MLog.info(TAG, "[getActivityContext] context== " + context);
        }
        return context;
    }

}
