package me.hutcwp.demo.base.mvp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import hut.cwp.api.Injector;
import me.hutcwp.demo.base.util.MLog;

/**
 * A Activity that uses an {@link MvpPresenter} to implement a Model-View-Presenter
 */

public class MvpActivity<P extends MvpPresenter<V>, V extends MvpView> extends RxFragmentActivity
        implements MvpView {

    private static final String TAG = "MvpActivity";

    protected P mPresenter;

    public P createPresenter() {
        if (mPresenter == null) {
            mPresenter = getPresenterBinder(getMvpView());
        }
        return mPresenter;
    }

    @NonNull
    public P getPresenter() {
        return mPresenter;
    }

    public void setPresenter(@NonNull P presenter) {
        mPresenter = presenter;
    }

    @NonNull
    public V getMvpView() {
        return (V) this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
        Injector.injectContainer(this);
        if (mPresenter != null) {
            mPresenter.attachView(getMvpView());
            mPresenter.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
    }

    private P getPresenterBinder(V v) {
        Class<?> tClass = v.getClass();
        boolean isBindPresenter = tClass.isAnnotationPresent(BindPresenter.class);
        if (isBindPresenter) {
            BindPresenter annotation = tClass.getAnnotation(BindPresenter.class);
            try {
                MLog.debug(TAG, "create presenter instance success");
                P p = (P) annotation.presenter().newInstance();
                return p;
            } catch (java.lang.InstantiationException e1) {
                MLog.error(TAG, "create presenter fail : " + e1.getMessage());
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public void autoLoadComponent(@IdRes int resId, MvpFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(resId, fragment)
                .commitAllowingStateLoss();
    }
}
