package com.techzealot.spring.playground.aop.manual;

import com.techzealot.spring.playground.aop.aspectj.ExecutionTime;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class TestManualService {
    @ExecutionTime(unit = ChronoUnit.SECONDS)
    public void execute() throws InterruptedException {
        long timeout = ThreadLocalRandom.current().nextLong(3000);
        TimeUnit.MILLISECONDS.sleep(timeout);
        //内部调用AOP失效
        innerCall();
    }

    @ExecutionTime(unit = ChronoUnit.SECONDS)
    public void innerCall() throws InterruptedException {
        long timeout = ThreadLocalRandom.current().nextLong(2000);
        TimeUnit.MILLISECONDS.sleep(timeout);
    }
}
