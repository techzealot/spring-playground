package com.techzealot.aspectj;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Test {

    @ExecutionTime(unit = ChronoUnit.NANOS)
    public static void execute() throws InterruptedException {
        long timeout = ThreadLocalRandom.current().nextLong(600);
        TimeUnit.MILLISECONDS.sleep(timeout);
        innerCall();
    }

    @ExecutionTime(unit = ChronoUnit.NANOS)
    public static void innerCall() throws InterruptedException {
        long timeout = ThreadLocalRandom.current().nextLong(500);
        TimeUnit.MILLISECONDS.sleep(timeout);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            execute();
        }
    }
}
