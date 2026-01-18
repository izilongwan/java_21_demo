package com.java_21_demo.web.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class ProxyUtil {
    public static <T> T proxy(T target, InvocationHandler h) {
        Class<? extends Object> c = target.getClass();
        return (T) Proxy.newProxyInstance(c.getClassLoader(), c.getInterfaces(), h);
    }
}
