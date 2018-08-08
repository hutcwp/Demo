package me.hutcwp.demo.base.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import me.hutcwp.demo.api.CompatOptional;


/**
 * base presenter for referring to the attach view
 *
 * @author huangfan(kael)
 * @time 2017/7/26 21:30
 */

public class MvpPresenter<V extends MvpView> {

    private V viewRef;

    @Nullable
    protected V getView(){
        return viewRef;
    }

    /**
     * 保护空指针
     * @return
     */
    protected CompatOptional<V> getMvpView(){
        return CompatOptional.ofNullable(getView());
    }

    /**
     * 更新activity/fragment中Bundle
     * @param arguments
     */
    public boolean setArguments(Bundle arguments){
        if(viewRef != null){
            if(viewRef instanceof Fragment){
                ((Fragment) viewRef).setArguments(arguments);
                return true;
            }else if(viewRef instanceof Activity && ((Activity) viewRef).getIntent() != null){
                ((Activity) viewRef).getIntent().putExtras(arguments);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取activity/fragment中Bundle
     * @return
     */
    public Bundle getArguments(){
        if(viewRef != null){
            if(viewRef instanceof Activity && ((Activity) viewRef).getIntent() != null){
                return ((Activity) viewRef).getIntent().getExtras();
            }else if(viewRef instanceof Fragment){
                return ((Fragment) viewRef).getArguments();
            }
        }
        return null;
    }

    /**
     * attach fragment/activity  指针
     * @param view
     */
    protected void attachView(V view){
        viewRef = view;
    }

    /**
     * 对应 activity/fragment onCreate
     */
    protected void onCreate(Bundle savedInstanceState){}

    /**
     * 对应 activity/fragment onDestroy
     */
    protected void onDestroy() {
        if (viewRef != null) {
            viewRef = null;
        }
    }

    /**
     * 对应 activity/fragment onStart
     */
    protected void onStart(){}

    /**
     * 对应 activity/fragment onResume
     */
    protected void onResume(){}

    /**
     * 对应 activity/fragment onPause
     */
    protected void onPause(){}

    /**
     * 对应 activity/fragment onStop
     */
    protected void onStop(){}

    protected void detachView() {}
}
