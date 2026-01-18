package com.java_21_demo.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Configuration
public class MicoAppCommonWebResponseAspect {
    @Pointcut("execution(public * *..controller.*Controller.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long startTs = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        Long timeTs = System.currentTimeMillis() - startTs;

        ServletRequestAttributes attrs = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());

        attrs.setAttribute("timecost", timeTs, 0);

        return proceed;
    }
}
