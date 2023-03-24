package com.techzealot.spring.playground.aop.aspectj;

import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class TestService {

    @ExecutionTime(unit = ChronoUnit.NANOS)
    public void execute() throws InterruptedException {
        long timeout = ThreadLocalRandom.current().nextLong(600);
        TimeUnit.MILLISECONDS.sleep(timeout);
        //内部调用AOP失效
        innerCall();
    }

    @ExecutionTime(unit = ChronoUnit.NANOS)
    public void innerCall() throws InterruptedException {
        long timeout = ThreadLocalRandom.current().nextLong(500);
        TimeUnit.MILLISECONDS.sleep(timeout);
    }
}
