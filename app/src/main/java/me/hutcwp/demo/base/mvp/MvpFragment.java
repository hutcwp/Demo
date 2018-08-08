package me.hutcwp.demo.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * A Fragment that uses an {@link MvpPresenter} to implement a Model-View-Presenter
 *
 * @author huangfan(kael)
 * @time 2017/7/26 21:44
 */

public class MvpFragment<P extends MvpPresenter<V>, V extends MvpView> extends RxFragment
        implements MvpInnerDelegateCallback<P, V>, MvpView {

    protected P mPresenter;
    private MvpInnerDelegate<P, V> mMvpInnerDelegate;

    @Override
    public P createPresenter() {
        if (mPresenter == null) {
            mPresenter = getMvpDelegate().createPresenter();
        }
        return mPresenter;
    }

    @NonNull
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        mPresenter = presenter;
    }

    @Override
    @NonNull
    public V getMvpView() {
        return (V) this;
    }

    @Override
    @NonNull
    public MvpInnerDelegate<P, V> getMvpDelegate() {
        if (mMvpInnerDelegate == null) {
            mMvpInnerDelegate = onCreateDelegate();
        }
        return mMvpInnerDelegate;
    }

    protected MvpInnerDelegate<P, V> onCreateDelegate() {
        return new MvpInnerDelegate<>(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
        getMvpDelegate().attachView(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        getMvpDelegate().detach();
    }
}
