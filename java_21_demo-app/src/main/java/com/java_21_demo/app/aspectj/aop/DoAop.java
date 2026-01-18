package com.java_21_demo.app.aspectj.aop;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.java_21_demo.app.aspectj.DoAspecj;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class DoAop {

    // 拦截单个 @DoAspecj 注解或 @DosAspectj 容器注解
    @Pointcut("@annotation(com.java_21_demo.app.aspectj.DoAspecj) || @annotation(com.java_21_demo.app.aspectj.DosAspectj)")
    public void pointMethod() {
    }

    // 拦截类上的注解
    @Pointcut("@within(com.java_21_demo.app.aspectj.DoAspecj) || @within(com.java_21_demo.app.aspectj.DosAspectj)")
    public void pointClass() {
    }

    @Around("pointMethod() || pointClass()")
    @SuppressWarnings("unchecked")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("===== DoAop around method invoked =====");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DoAspecj[] annotations = signature.getMethod().getAnnotationsByType(DoAspecj.class);

        // 如果方法上没有注解，则获取类上的注解
        if (annotations.length == 0) {
            annotations = (DoAspecj[]) signature.getDeclaringType()
                    .getAnnotationsByType(DoAspecj.class);
        }

        long start = System.currentTimeMillis();
        log.info("[DoAop] Start: {}.{}", signature.getDeclaringTypeName(), signature.getName());

        try {
            return joinPoint.proceed();
        } finally {
            long cost = System.currentTimeMillis() - start;
            String annotationValues = Arrays.stream(annotations)
                    .map(DoAspecj::value)
                    .collect(Collectors.joining(", "));
            log.info("[DoAop] {}.{} took {} ms, annotation values: {}",
                    signature.getDeclaringTypeName(),
                    signature.getName(), cost, annotationValues);
        }
    }
}
