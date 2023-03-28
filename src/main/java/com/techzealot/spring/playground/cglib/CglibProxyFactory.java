package com.techzealot.spring.playground.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.util.List;

public class CglibProxyFactory {
    /**
     * 生成内部调用不生效的代理，spring AOP普通代理实现方式
     *
     * @param clazz
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T getCallTargetProxy(Class<T> clazz, T target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            String methodName = method.getName();
            if (List.of("execute", "innerCall").contains(methodName)) {
                System.out.println("target: before " + methodName);
                //不能使用该调用会触发死循环
                //proxy.invoke(obj,args);
                Object result = proxy.invoke(target, args);
                System.out.println("target: after " + method.getName());
                return result;
            } else {
                return proxy.invoke(target, args);
            }
        });
        return (T) enhancer.create();
    }

    /**
     * 生成内部调生效的代理，spring Configuration class代理实现方式
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getCallProxyProxy(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            String methodName = method.getName();
            if (List.of("execute", "innerCall").contains(methodName)) {
                System.out.println("proxy: before " + methodName);
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("proxy: after " + method.getName());
                return result;
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });
        return (T) enhancer.create();
    }
}
