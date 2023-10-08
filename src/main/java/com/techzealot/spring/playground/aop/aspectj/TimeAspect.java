package com.techzealot.spring.playground.aop.aspectj;


import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class TimeAspect {

    private static Logger logger = LoggerFactory.getLogger(TimeAspect.class);

    /**
     * spring aop没有aspectj的只配置注解会调用两次bug
     *
     * @param executionTime
     */
    @Pointcut("@annotation(executionTime)")
    public void callAt(ExecutionTime executionTime) {
    }


    @Around("callAt(executionTime)")
    public Object around(ProceedingJoinPoint pjp, ExecutionTime executionTime) throws Throwable {
        logger.info("before advice");
        long start = System.nanoTime();
        Object proceed = pjp.proceed();
        long end = System.nanoTime();
        logger.info("method {} execution time: {} {}",
            pjp.getSignature().getName(),
            Duration.of(end - start, ChronoUnit.NANOS).get(executionTime.unit()),
            executionTime.unit().name());
        return proceed;
    }
}
