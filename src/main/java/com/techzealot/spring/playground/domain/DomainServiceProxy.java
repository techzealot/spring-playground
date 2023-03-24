package com.techzealot.spring.playground.domain;

public class DomainServiceProxy extends DomainService {
    private DomainService target;

    public DomainServiceProxy() {
    }

    public void setTarget(DomainService target) {
        this.target = target;
    }

    @Override
    public void testA() {
        //before advice
        target.testA();
        //after advice
    }

    @Override
    public void testB() {
        //before advice
        target.testB();
        //after advice
    }
}
