package me.hutcwp.demo.base.mvp;

import android.support.annotation.NonNull;

/**
 * @author huangfan(kael)
 * @time 2017/7/31 16:32
 */

public interface MvpInnerDelegateCallback<P extends MvpPresenter<V>, V extends MvpView> {

    @NonNull
    P createPresenter();

    void setPresenter(@NonNull P presenter);

    @NonNull
    P getPresenter();

    V getMvpView();

    @NonNull
    MvpInnerDelegate<P, V> getMvpDelegate();
}
