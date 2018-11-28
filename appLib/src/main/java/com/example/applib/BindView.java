package com.example.applib;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BindView {
    public static void bind(Activity activity) {
        String classFullName = activity.getClass().getName() + "$$ViewInjector";
        try {
            Class<?> clazz = Class.forName(classFullName);
            Constructor constructor = clazz.getConstructor(activity.getClass());
            constructor.newInstance(activity);
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
    }
}
