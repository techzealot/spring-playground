package com.techzealot.spring.playground.ioc.circular;

import org.springframework.stereotype.Service;

@Service
public class BService {
    private AService aService;

    public BService(AService aService) {
        this.aService = aService;
        System.out.println("init BService");
    }
}
