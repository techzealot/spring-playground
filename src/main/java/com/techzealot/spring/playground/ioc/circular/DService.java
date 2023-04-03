package com.techzealot.spring.playground.ioc.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class DService {
    @Autowired
    private AService aService;

    public DService() {
        System.out.println("init DService");
    }

    public void test() {
        System.out.println("run DService test");
    }
}
