package com.hutcwp;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射专用
 */
public class RefInvoke {

    public static Object createObj(String className, Class[] pareTypes, Object[] pareVaules) {
        try {
            Class r = Class.forName(className);
            Constructor constructor = r.getDeclaredConstructor(pareTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(pareVaules);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeInstanceMethod(Object obj, String methodName, Class[] pareTyples, Object[] pareVaules) {
        if (obj == null) {
            return null;
        }

        Method method = null;
        try {
            method = obj.getClass().getMethod(methodName, pareTyples);
            method.setAccessible(true);
            return method.invoke(obj, pareVaules);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFiledObject(String className, Object obj, String filedName) {
        try {
            Field filedObject = Class.forName(className).getDeclaredField(filedName);
            filedObject.setAccessible(true);
            return filedObject.get(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setFiled(String className, Object obj, String filedName, Object filedObject) {

        try {
            Field field = Class.forName(className).getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(obj, filedObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
