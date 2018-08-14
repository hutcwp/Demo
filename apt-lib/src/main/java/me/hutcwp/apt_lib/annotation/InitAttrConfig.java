package me.hutcwp.apt_lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface InitAttrConfig {
    //    InitLevel initLevel();
    int resourceId();

    Class component();
}
