package com.hutcwp.livebiz.api;

import android.util.Log;

import java.lang.annotation.Annotation;

/**
 * @author huangfan(kael)
 * @time 2017/7/27 17:44
 */

public class MvpApi {

    private static final String ANNOTATION_DELEGATE_BIND = "com.yy.android.sniper.annotation.mvp.DelegateBind";

    private static Class CLASS_DELEGATE_BIND;

    /**
     * 根据MVP中的v生成对应PresenterBinder类
     * @param v
     * @param <P>
     * @param <V>
     * @return
     */
    public static <P, V> PresenterBinder<P, V> getPresenterBinder(V v){
        Class<?> tClass = v.getClass();
        boolean delegate = false;
        try{
            if(CLASS_DELEGATE_BIND == null){
                CLASS_DELEGATE_BIND = Class.forName(ANNOTATION_DELEGATE_BIND);
            }
            if(null != tClass.getAnnotation(CLASS_DELEGATE_BIND))delegate = true;
        }catch(ClassNotFoundException e){
            Log.e("MvpApi getPresenter", String.format("classloader load class %s fail, reason is %s", ANNOTATION_DELEGATE_BIND, e.getMessage()));
            for(Annotation a : tClass.getAnnotations()){
                if(a.annotationType().getName().equalsIgnoreCase(ANNOTATION_DELEGATE_BIND)){
                    delegate = true;
                    break;
                }
            }
        }
        if(delegate){
            String clsName = tClass.getName();
            try{
                Class<?> presenterBinderClass = Class.forName(clsName + "$$PresenterBinder");
                return (PresenterBinder<P, V>) presenterBinderClass.newInstance();
            }catch (Exception e) {
                Log.e("MvpApi getPresenter", "create Presenter fail, reason is "+e.getMessage());
            }
        }
        return null;
    }

}
