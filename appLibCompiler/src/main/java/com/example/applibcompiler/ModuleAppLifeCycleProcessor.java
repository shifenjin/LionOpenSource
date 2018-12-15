package com.example.applibcompiler;

import com.example.applibannotation.ModuleAppLifeCycle;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

// 需要处理的注解
@SupportedAnnotationTypes({"com.example.applibannotation.ModuleAppLifeCycle"})
// 支持的源码版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
// 生成的METE-INF信息
@AutoService(Processor.class)
public class ModuleAppLifeCycleProcessor extends AbstractProcessor {

    private List<TypeElement> mTypeElementList;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mTypeElementList = new ArrayList<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mTypeElementList.clear();

        // 收集所有注解相关数据
        collectInfo(roundEnvironment);
        // 生成java文件
        writeToFile();

        return false;
    }

    private void collectInfo(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(ModuleAppLifeCycle.class)) {
            TypeElement typeElement = (TypeElement) element;
            mTypeElementList.add(typeElement);

//            ExecutableElement executableElement = (ExecutableElement) element;
//
//            // 参数不为空不处理
//            if (!executableElement.getParameters().isEmpty()) {
//                continue;
//            }
//
//            // 如果不为静态方法，忽略
//
//            String classFullName = null;
//            TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
//            classFullName = typeElement.getQualifiedName().toString();
//
//            String methodName = null;
//            methodName = executableElement.getSimpleName().toString();
//
//            if (classMethodsMap.containsKey(classFullName)) {
//                List<String> methodList = classMethodsMap.get(classFullName);
//                methodList.add(methodName);
//            } else {
//                List<String> methodList = new ArrayList<>();
//                methodList.add(methodName);
//                classMethodsMap.put(classFullName, methodList);
//            }

        }
    }

    private void writeToFile() {
        if (!mTypeElementList.isEmpty()) {
            for (TypeElement typeElement : mTypeElementList) {
                MethodSpec method = MethodSpec.methodBuilder("getRealClassFullName")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(String.class)
                        .addStatement("return $S", typeElement.getQualifiedName())
                        .build();

                TypeSpec typeSpec =
                        TypeSpec.classBuilder(typeElement.getSimpleName() + "$$Proxy")
                                .addModifiers(Modifier.PUBLIC)
                                .addMethod(method)
                                .build();

                JavaFile javaFile = JavaFile.builder("com.example.moduleAppLifecycleProxy", typeSpec).build();

                // test
                System.out.println(javaFile.toString());

                try {
                    javaFile.writeTo(processingEnv.getFiler());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//        if (!classMethodsMap.values().isEmpty()) {
//
//            for (Map.Entry<String, List<String>> entry : classMethodsMap.entrySet()) {
//                // 构建 构造函数
//                MethodSpec.Builder constructorBuild = MethodSpec.constructorBuilder()
//                        .addModifiers(Modifier.PUBLIC);
//
//                String classFullName = entry.getKey();
//                List<String> methodList = entry.getValue();
//                if (methodList != null && !methodList.isEmpty()) {
//                    for (String methodName : methodList) {
//                        constructorBuild.addStatement("$L.$L()", classFullName, methodName);
//                    }
//                }
//
//                // 构建 类
//                TypeSpec typeSpec =
//                        TypeSpec.classBuilder(new StringBuilder()
//                                .append(.append("$$Proxy").toString())
//                                .addModifiers(Modifier.PUBLIC)
//                                .addMethod(constructorBuild.build())
//                                .build();
//
//                // 构建文件
//                JavaFile javaFile = JavaFile.builder("com.example.applicationOnCreate", typeSpec).build();
//                try {
//                    javaFile.writeTo(processingEnv.getFiler());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
