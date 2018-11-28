package com.example.applibannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 适用对象 - 类、接口、枚举
@Target(ElementType.FIELD)
// 生命周期 - 编译时
@Retention(RetentionPolicy.CLASS)
public @interface CustomBindViewAnnotation {
    int viewId();
}
