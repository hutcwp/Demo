package me.hutcwp.apt_api;

/**
 * Created by hutcwp on 2018/4/19.
 */


public interface ViewInject<T> {
    void inject(T t, Object source);
}
