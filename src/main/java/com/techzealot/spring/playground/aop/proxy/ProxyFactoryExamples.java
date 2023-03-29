package com.techzealot.spring.playground.aop.proxy;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyFactoryExamples {
    public static class TestObject {
        public void test() {
            System.out.println("run test");
        }
    }

    public static interface TestInterface {
        void execute();
    }

    public static class TestInterfaceImpl implements TestInterface {

        @Override
        public void execute() {
            System.out.println("run execute");
        }
    }

    public static void testJDKProxy() {
        TestInterface proxy = createJDKProxy(new TestInterfaceImpl(), TestInterface.class);
        proxy.execute();
    }

    public static void testCglibProxy() {
        TestObject proxy = (TestObject) createCglibProxy(new TestObject());
        proxy.test();
    }

    /**
     * 需要执行被代理对象逻辑时需要传入target对象
     *
     * @param sourceTarget
     * @param interfaces
     * @param <T>
     * @return
     */
    public static <T> T createJDKProxy(T sourceTarget, Class<T>... interfaces) {
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(sourceTarget);
        factory.setInterfaces(interfaces);
        factory.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
            System.out.println("before " + method.getName() + " target: " + target);
        });
        return (T) factory.getProxy();
    }

    public static <T> T createCglibProxy(T sourceTarget) {
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(sourceTarget);
        factory.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
            System.out.println("before " + method.getName() + " target: " + target);
        });
        return (T) factory.getProxy();
    }

    public static void main(String[] args) {
        testJDKProxy();
        testCglibProxy();
    }

}
