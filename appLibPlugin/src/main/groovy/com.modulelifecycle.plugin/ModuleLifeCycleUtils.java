package com.modulelifecycle.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLifeCycleUtils {

    // ModuleAAppLifeCycle$$Proxy class文件 的 包名前缀
    private static final String PROXY_CLASS_PACKAGE_NAME = "com/example/moduleAppLifecycleProxy";

    private static final String REGISTER_CLASS_FILE_NAME = "com/example/applib/moudule_applifecycle_helpr/ModuleAppLifeCycleHelper.class";

    public static File JAR_FILE_CONTAINS_INIT_CLASS;
    /**
     * 判断是不是ModuleAppLifeCycle$$Proxy.class文件
     * @return
     */
    public static boolean isModuleAppLifeCycleProxyClass(String fileName) {
        if (fileName.startsWith(PROXY_CLASS_PACKAGE_NAME)
                && fileName.endsWith("$$Proxy.class"))
            return true;
        else
            return false;
    }

    /**
     * 过滤 jar文件，找出是ModuleAppLifeCycle$$Proxy.class文件的文件名
     *
     * @param file
     * @param contentLocationFile
     * @return
     */
    public static List<String> scanJar(File file, File contentLocationFile) {
//        System.out.println("JarInput - 没过滤->" + file.getName());
        // 过滤非.jar文件
        if (!file.getAbsolutePath().endsWith(".jar")) {
//            System.out.println("JarInput - 非.jar ->" + file.getName());
            return null;
        }

        // 过滤com.android.support的jar文件
        if (file.getAbsolutePath().contains("com.android.support")) {
//            System.out.println("JarInput - com.android.support ->" + file.getName());
            return null;
        }

        List<String> destClassNameList = new ArrayList();

        try {
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            // 遍历.jar文件里的所有.class文件
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();

                // 匹配注入文件
                if (entryName.equals(REGISTER_CLASS_FILE_NAME)) {
                    System.out.println("匹配到需要注入的文件 -> " + entryName);
                    JAR_FILE_CONTAINS_INIT_CLASS = contentLocationFile;
                }

                // 匹配目标.class文件
                if (isModuleAppLifeCycleProxyClass(entryName)) {
                    destClassNameList.add(entryName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destClassNameList;

    }

    /**
     * 获取文件夹下所有文件资源
     *
     * @param file
     * @return
     */
    public static List<File> getEachFileRecurse(File file) {
        ArrayList<File> fileList = new ArrayList<>();

        recurGetFile(fileList, file);

        return fileList;
    }

    private static void recurGetFile(List<File> fileList, File file) {
        try {
            if (file == null)
                return;

            if (!file.isDirectory()) {
                fileList.add(file);
            } else {
                for (File subFile : file.listFiles())
                    recurGetFile(fileList, subFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
