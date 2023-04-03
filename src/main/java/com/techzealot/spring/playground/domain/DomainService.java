package com.techzealot.spring.playground.domain;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DomainService {

    @Transactional
    public void testA() {
        //testB事务失效:调用者是被代理对象而非增强后的对象
        this.testB();
        //do business A
    }

    @Transactional
    public void testB() {
        //do business B
    }
}
