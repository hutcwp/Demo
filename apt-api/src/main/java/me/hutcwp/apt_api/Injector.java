package me.hutcwp.apt_api;

import android.app.Activity;

/**
 * Created by hutcwp on 2018/4/19.
 */


public class Injector {
    private static final String VIEW_SUFFIX = "$$ViewInject";
    private static final String CONTAINER_SUFFIX = "$$FragmentInject";

    public static void inject(Activity activity) {
        Inject proxyActivity = findProxyActivity(activity, VIEW_SUFFIX);
        proxyActivity.inject(activity, activity);
    }

    public static void injectContainer(Activity activity) {
        Inject proxyActivity = findProxyActivity(activity, CONTAINER_SUFFIX);
        proxyActivity.inject(activity, activity);
    }

    private static Inject findProxyActivity(Object activity, String suffix) {
        try {
            Class clazz = activity.getClass();
            Class injectorClazz = Class.forName(clazz.getName() + suffix);
            return (Inject) injectorClazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(String.format("can not find %s , something when compiler.", activity.getClass().getSimpleName() + suffix));
    }
}
