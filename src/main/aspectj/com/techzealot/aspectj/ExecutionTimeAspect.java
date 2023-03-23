package com.techzealot.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Aspect
public class ExecutionTimeAspect {

    /**
     * ajc编译器bug,需要增加这个条件execution(* *(..)),否则会被增强两次
     * @param executionTime
     */
    @Pointcut("execution(* *(..))&&@annotation(executionTime)")
    public void callAt(ExecutionTime executionTime) {
    }


    @Around("callAt(executionTime)")
    public Object around(ProceedingJoinPoint pjp, ExecutionTime executionTime) throws Throwable {
        long start = System.nanoTime();
        Object proceed = pjp.proceed();
        long end = System.nanoTime();
        System.out.println(String.format("[%s] method [%s] execution time: %s %s",
                Thread.currentThread().getName(),
                pjp.getSignature().getName(),
                Duration.of(end - start, ChronoUnit.NANOS).get(executionTime.unit()),
                executionTime.unit().name()));
        return proceed;
    }

}