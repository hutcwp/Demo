package me.hutcwp.apt_api;

/**
 * Created by hutcwp on 2018/4/19.
 */


public interface Inject<T> {
    void inject(T t, Object source);
}
