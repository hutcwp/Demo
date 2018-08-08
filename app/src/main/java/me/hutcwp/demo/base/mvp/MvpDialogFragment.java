package me.hutcwp.demo.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.components.support.RxDialogFragment;


/**
 * Created by 张宇 on 2017/9/29.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
public class MvpDialogFragment<P extends MvpPresenter<V>, V extends MvpView> extends RxDialogFragment
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

    @Override
    public void setPresenter(@NonNull P presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @NonNull
    @Override
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
