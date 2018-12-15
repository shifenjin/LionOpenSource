package com.example.applibcompiler;

import com.example.applibannotation.CustomBindViewAnnotation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

// 需要处理的注解
@SupportedAnnotationTypes({"com.example.applibannotation.CustomBindViewAnnotation"})
// 支持的源码版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
// 生成的METE-INF信息
@AutoService(Processor.class)
public class CustomBVAnnotationProcessor extends AbstractProcessor {

    private Elements mElementUtils;
    private Filer mFiler;

    // 存放每一个class下的变量信息
    private Map<String, List<VariableInfo>> variableInfoListMap;
    // k - classFullName, v - classElement
    private Map<String, TypeElement> classElementMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mElementUtils = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();

        variableInfoListMap = new HashMap<>();
        classElementMap = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 清空 variableInfoListMap
        variableInfoListMap.clear();

        // 收集所有注解相关数据
        collectInfo(roundEnvironment);
        // 生成java文件
        writeToFile();


        return false;
    }

    private void writeToFile() {
        for (String classFullName : classElementMap.keySet()) {

            TypeElement classElement = classElementMap.get(classFullName);

            // 构建 构造函数
            MethodSpec.Builder constructorBuilder =
                    MethodSpec.constructorBuilder()
                            // 构造函数 - 修饰符
                            .addModifiers(Modifier.PUBLIC)
                            // 构造函数 - 参数
                            .addParameter(ParameterSpec.builder(TypeName.get(classElement.asType()), "activity").build());
            List<VariableInfo> variableInfoList = variableInfoListMap.get(classFullName);
            if (variableInfoList != null) {
                for (VariableInfo variableInfo : variableInfoList) {
                    int viewId = variableInfo.viewId;
                    String viewElementName = variableInfo.variableElement.getSimpleName().toString();
                    String viewElementType = variableInfo.variableElement.asType().toString();
                    // 构造函数 - 内容
                    constructorBuilder.addStatement("activity.$L = ($L)activity.findViewById($L)",
                            viewElementName,
                            viewElementType,
                            viewId);
                }
            }

            // 构建 类
            TypeSpec typeSpec =
                    TypeSpec.classBuilder(classElement.getSimpleName().toString() + "$$ViewInjector")
                            .addModifiers(Modifier.PUBLIC)
                            .addMethod(constructorBuilder.build())
                            .build();

            // 构建文件
            // 包名
            String pkgFullName = mElementUtils.getPackageOf(classElement).getQualifiedName().toString();
            JavaFile javaFile = JavaFile.builder(pkgFullName, typeSpec).build();

            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void collectInfo(RoundEnvironment roundEnvironment) {
        // 遍历所有被注解的元素
        for (Element element : roundEnvironment.getElementsAnnotatedWith(CustomBindViewAnnotation.class)) {
            // 处理元素类型为类的元素
            if (element.getKind() == ElementKind.FIELD) {
                // 获取 元素注解的值（控件资源id）
                int valueId = element.getAnnotation(CustomBindViewAnnotation.class).value();
                // 将 元素 转换为 变量元素（成员变量）
                VariableElement variableElement = (VariableElement) element;
                // 获取 封装此元素的最里层元素（封装该成员变量的类元素）
                TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
                // 获取 Type元素的绝对路径（类元素的路径）
                String classFullName = classElement.getQualifiedName().toString();

                if (variableInfoListMap.containsKey(classFullName)) {
                    List<VariableInfo> variableInfoList = variableInfoListMap.get(classFullName);
                    variableInfoList.add(new VariableInfo(valueId, variableElement));
                } else {
                    classElementMap.put(classFullName, classElement);

                    List<VariableInfo> variableInfoList = new ArrayList<>();
                    variableInfoList.add(new VariableInfo(valueId, variableElement));
                    variableInfoListMap.put(classFullName, variableInfoList);
                }
            }
        }
    }

}
