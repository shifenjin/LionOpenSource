package com.example.applib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ApplicationOnCreateHelper {
    public static void init() {
        String classFullName = "com.example.applicationOnCreate.ApplicationOnCreate$$Proxy";
        try {
            Class<?> clazz = Class.forName(classFullName);
            Constructor constructor = clazz.getConstructor();
            constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
