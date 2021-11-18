package com.example.applib.moudule_applifecycle_helpr;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ModuleAppLifeCycleHelper {

    private static ModuleAppLifeCycleHelper mInstance;

    List<IModuleAppLifeCycle> mModuleAppLifeCycleList;

    public static ModuleAppLifeCycleHelper getInstance() {
        if (mInstance == null)
            synchronized (ModuleAppLifeCycleHelper.class) {
                if (mInstance == null)
                    mInstance = new ModuleAppLifeCycleHelper();
            }

        return mInstance;
    }

    private ModuleAppLifeCycleHelper() {
        mModuleAppLifeCycleList = new ArrayList<>();

        init();
    }

    private void register(String classNameProxy) {

        try {
            Class<?> clazzProxy = Class.forName(classNameProxy);
            Method method = clazzProxy.getMethod("getRealClassFullName");
            String className = (String) method.invoke(null);
            Class<?> clazz = Class.forName(className);
            IModuleAppLifeCycle moduleAppLifeCycle = (IModuleAppLifeCycle) clazz.newInstance();
            mModuleAppLifeCycleList.add(moduleAppLifeCycle);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void init() {
    }

    public void notifyApplicationOnCreate(Application application) {
        for (IModuleAppLifeCycle moduleAppLifeCycle : mModuleAppLifeCycleList) {
            moduleAppLifeCycle.applicationOnCreate(application);
        }
    }
}
