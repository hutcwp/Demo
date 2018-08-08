package me.hutcwp.demo.base.mvp;

import android.os.Bundle;

import me.hutcwp.demo.api.MvpApi;
import me.hutcwp.demo.api.PresenterBinder;


/**
 * a delegate for {@link MvpFragment} or {@link MvpActivity} handle some logic
 *
 * @author huangfan(kael)
 * @time 2017/7/31 16:27
 */

public class MvpInnerDelegate<P extends MvpPresenter<V>, V extends MvpView> {

    private MvpInnerDelegateCallback<P, V> mMvpInnerDelegateCallback;
    private PresenterBinder<P, V> mPresenterBinder;

    public MvpInnerDelegate(MvpInnerDelegateCallback<P, V> mvpInnerDelegateCallback) {
        mMvpInnerDelegateCallback = mvpInnerDelegateCallback;
    }

    /**
     * createPresenter封装在delegate中，避免在activity/fragment中多次出现
     *
     * @return
     */
    public P createPresenter() {
        P presenter = null;
        if(mPresenterBinder == null){
            mPresenterBinder = MvpApi.getPresenterBinder(mMvpInnerDelegateCallback.getMvpView());
            if (mPresenterBinder != null) {
                presenter = mPresenterBinder.bindPresenter(mMvpInnerDelegateCallback.getMvpView());
            }
        }
        return presenter != null ? presenter : (P) new MvpPresenter<>();
    }

    /**
     * attach MvpView{@link MvpActivity}/{@link MvpFragment} to {@link MvpPresenter}
     *
     * @param savedInstanceState
     */
    public void attachView(Bundle savedInstanceState) {
        if(mMvpInnerDelegateCallback instanceof MvpView){
            mMvpInnerDelegateCallback.getPresenter().attachView(mMvpInnerDelegateCallback.getMvpView());
            mMvpInnerDelegateCallback.getPresenter().onCreate(savedInstanceState);
        }
    }

    /**
     * detach PresenterBinder<P, V>,
     * 解除MvpView{@link MvpActivity}/{@link MvpFragment} 对 {@link MvpPresenter}中的数据绑定
     */
    public void detach(){
        mMvpInnerDelegateCallback.getPresenter().detachView();

        if(mPresenterBinder != null){
            mPresenterBinder.unbindPresenter();
        }
        mPresenterBinder = null;
    }
}
