package com.techzealot.spring.playground.ioc.circular;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class CService {

    public CService() {
        System.out.println("init CService");
    }
}
