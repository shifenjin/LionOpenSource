package com.example.lion_personal.lionopensource.aop;

import android.util.Log;

import com.example.lion_personal.lionopensource.aop.annotation.BehaviorTimeTrace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class MethodBehaviorAspect {

    @Pointcut("execution(@com.example.lion_personal.lionopensource.aop.annotation.BehaviorTimeTrace * *(..))")
    public void behaviorTimeTrace() {}

    @Around("behaviorTimeTrace()")
    public Object behaviorTimeTraceAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 方法特性
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 类名
        String classSimpleName = methodSignature.getDeclaringType().getSimpleName();
        // 方法名
        String methodName = methodSignature.getName();
        // 注解值 - 是否打印log
        boolean isLog = methodSignature.getMethod().getAnnotation(BehaviorTimeTrace.class).isLog();

        long begin = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long durationSecond = end - begin;

        if (isLog)
            Log.i("BehaviorTimeTrace", String.format("类 %s - 方法 %s 执行耗时：%s", classSimpleName, methodName, durationSecond));

        return result;
    }
}
