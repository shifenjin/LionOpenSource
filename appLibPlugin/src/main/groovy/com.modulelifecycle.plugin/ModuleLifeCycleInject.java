package com.modulelifecycle.plugin;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class ModuleLifeCycleInject {
    private String INJECT_CLASS_FILE_NAME = "com/example/applib/moudule_applifecycle_helpr/ModuleAppLifeCycleHelper.class";

    private final List<String> mModuleAppLifeCycleProxyClassNameList;

    public ModuleLifeCycleInject(List<String> moduleAppLifeCycleProxyClassNameList) {
        this.mModuleAppLifeCycleProxyClassNameList = moduleAppLifeCycleProxyClassNameList;
    }

    public void inject() {
        try {
            File originFile = ModuleLifeCycleUtils.JAR_FILE_CONTAINS_INIT_CLASS;
            JarFile originJarFile = new JarFile(originFile);

            //创建一个新的jar文件，注入字节码
            File file = new File(originFile.getParent(), originFile.getName() + ".opt");
            if (file.exists())
                file.delete();
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(file));

            Enumeration<JarEntry> enumeration = originJarFile.entries();
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = enumeration.nextElement();
                String entryName = jarEntry.getName();
                ZipEntry zipEntry = new ZipEntry(entryName);
                jarOutputStream.putNextEntry(zipEntry);

                InputStream inputStream = originJarFile.getInputStream(jarEntry);
                System.out.println("遍历需要注解的Jar文件的class -> " + entryName + " " + entryName.equals(INJECT_CLASS_FILE_NAME));
                if (entryName.equals(INJECT_CLASS_FILE_NAME)) {
                    System.out.println("匹配到需要注入的类 -> " + entryName);
                    ClassReader classReader = new ClassReader(inputStream);
                    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    ClassVisitor classVisitor = new ModuleLifeCycleInjectVisitor(classWriter);
                    classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

                    byte[] bytes = classWriter.toByteArray();
                    jarOutputStream.write(bytes);
                } else {
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                inputStream.close();
                jarOutputStream.closeEntry();
            }
            originJarFile.close();
            jarOutputStream.close();

            // 删除原来的jar文件
            if (originFile.exists())
                originFile.delete();

            // 新的jar文件名字 替换成 原来的jar文件名字
            file.renameTo(originFile);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ModuleLifeCycleInjectVisitor extends ClassVisitor {

        public ModuleLifeCycleInjectVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
            System.out.println("ModuleLifeCycleInjectVisitor construct called -> " );
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            // 找到要修改的方法
            if (name.equals("init")) {
                System.out.println("visitMethod 找到要修改的方法 -> " + name);
                methodVisitor = new ModuleLifeCycleMethodAdapter(Opcodes.ASM5, methodVisitor, access, name, desc);
            }

            return methodVisitor;
        }
    }

    private class ModuleLifeCycleMethodAdapter extends AdviceAdapter {

        protected ModuleLifeCycleMethodAdapter(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();

            if (mModuleAppLifeCycleProxyClassNameList != null) {
                for (String className : mModuleAppLifeCycleProxyClassNameList) {
                    System.out.println("注入的className -> " + className);

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitLdcInsn(className);
                    mv.visitMethodInsn(INVOKESPECIAL, "com/example/applib/moudule_applifecycle_helpr/ModuleAppLifeCycleHelper", "register", "(Ljava/lang/String;)V", false);

//                    mv.visitLdcInsn(className);
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "com/example/applib/moudule_applifecycle_helpr/ModuleAppLifeCycleHelper", "register", "(Ljava/lang/String;)V", false);
                }
            }
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
        }
    }
}
