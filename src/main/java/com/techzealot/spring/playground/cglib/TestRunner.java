package com.techzealot.spring.playground.cglib;

public class TestRunner {
    public static void main(String[] args) {
        InnerCallTester callProxyTester = CglibProxyFactory.getCallProxyProxy(InnerCallTester.class);
        callProxyTester.execute();
        callProxyTester.innerCall();
        InnerCallTester callTargetTester = CglibProxyFactory.getCallTargetProxy(InnerCallTester.class, new InnerCallTester());
        callTargetTester.execute();
        callTargetTester.innerCall();
    }
}
