package com.modulelifecycle.plugin;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

// Gradle transform
class ModuleLifeCycleTransform extends Transform {

    private final Project project;

    public ModuleLifeCycleTransform(Project project) {
        this.project = project;
    }

    @Override
    public String getName() {
        return "ModuleLifeCycleTransform";
    }

    /**
     * 扫描类型(可选 class文件 和 资源文件)
     *
     * 这里指定为 .class
     *
     * @return
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * 扫描范围
     *
     * 这里指定为 全工程
     *
     * @return
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);

//        System.out.println("ModuleLifeCycleTransform - transform -> called");
//        long start = System.currentTimeMillis();
        List<String> moduleAppLifeCycleProxyClassFileNameList = new ArrayList();

        Collection<TransformInput> inputs = transformInvocation.getInputs();
        // 所有 class文件 和 jar包 集合
        for (TransformInput transformInput : inputs) {
            // 遍历所有的 class文件目录
            for (DirectoryInput directoryInput : transformInput.getDirectoryInputs()) {
//                System.out.println("class文件目录 - DirectoryInput ->" + directoryInput.getFile().getName());
                if (directoryInput.getFile().isDirectory()) {
//                    System.out.println("class文件目录 - DirectoryInput - isDirectory->" + directoryInput.getFile().getName());
                    // 遍历所有的 class文件
                    List<File> fileList = ModuleLifeCycleUtils.getEachFileRecurse(directoryInput.getFile());
                    for (File file : fileList) {
//                        System.out.println("class文件目录 ->" + file.getName());
                        // 如果是指定 class文件，缓存起来
                        if (ModuleLifeCycleUtils.isModuleAppLifeCycleProxyClass(file.getName())) {
//                            System.out.println("class文件目录 - 命中 ->" + file.getName());
                            moduleAppLifeCycleProxyClassFileNameList.add(file.getName());
                        }
                    }
                }

                // 输出
                File contentLocation = transformInvocation.getOutputProvider()
                        .getContentLocation(directoryInput.getName(),
                                directoryInput.getContentTypes(),
                                directoryInput.getScopes(),
                                Format.DIRECTORY);
                FileUtils.copyDirectory(directoryInput.getFile(), contentLocation);
            }

            // 遍历所有的 jar包
            for (JarInput jarInput : transformInput.getJarInputs()) {
                //与处理class文件一样，处理jar包也是一样，最后要将inputs转换为outputs
                String jarName = jarInput.getName();
                String md5 = DigestUtils.md5Hex(jarInput.getFile().getAbsolutePath());
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4);
                }
                //获取输出路径下的jar包名称，必须这样获取，得到的输出路径名不能重复，否则会被覆盖
                File contentLocationFile = transformInvocation.getOutputProvider().
                        getContentLocation(jarName + md5, jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);

                List<String> destClassNameList = ModuleLifeCycleUtils.scanJar(jarInput.getFile(), contentLocationFile);
                moduleAppLifeCycleProxyClassFileNameList.addAll(destClassNameList);

                FileUtils.copyFile(jarInput.getFile(), contentLocationFile);
            }

        }

        // class文件名 转换为 class类名
        List<String> moduleAppLifeCycleProxyClassNameList = new ArrayList<>();
        for (String className : moduleAppLifeCycleProxyClassFileNameList) {
            moduleAppLifeCycleProxyClassNameList.add(className.replace("/", ".").substring(0, className.length() - ".class".length()));
        }

        // 通过ASM注入字节码
        new ModuleLifeCycleInject(moduleAppLifeCycleProxyClassNameList).inject();

        for (String className : moduleAppLifeCycleProxyClassNameList) {
            System.out.println("moduleAppLifeCycleProxyClassName -> " + className);
        }
    }
}
