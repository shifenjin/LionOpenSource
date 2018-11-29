package com.example.applib;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InjectHelper {
    public static void bind(Activity activity) {
        String classFullName = activity.getClass().getName() + "$$ViewInjector";
        try {
//            Class<?> clazz = Class.forName("com.example.lion_personal.lionopensource.annotation.AnnotationActivity$$ViewInjector");
            Class<?> clazz = Class.forName(classFullName);
            Constructor constructor = clazz.getConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
