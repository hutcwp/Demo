package me.hutcwp.apt_lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hutcwp on 2018/4/19.
 */


@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindView
{
    int value();
}
