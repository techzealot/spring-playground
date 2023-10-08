package com.techzealot.spring.playground.aop.manual;


import com.techzealot.spring.playground.aop.aspectj.ExecutionTime;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnProperty(value = "aop.time.expression")
@Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
public class TimeAspectConfiguration {

    @Value("${aop.time.expression:}")
    private String expression;

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public PointcutAdvisor aspectJTimePointcutAdvisor() {
        AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        pointcutAdvisor.setExpression(expression);
        pointcutAdvisor.setAdvice(timeAroundAdvice());
        //先进后出
        pointcutAdvisor.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
        return pointcutAdvisor;
    }

    @Bean
    public TimeAroundAdvice timeAroundAdvice() {
        return new TimeAroundAdvice();
    }

    private static class TimeAroundAdvice implements MethodInterceptor {

        private static Logger logger = LoggerFactory.getLogger(TimeAroundAdvice.class);

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            logger.info("before advice");
            long start = System.nanoTime();
            Object proceed = invocation.proceed();
            long end = System.nanoTime();
            ExecutionTime anno = invocation.getMethod().getAnnotation(ExecutionTime.class);
            logger.info("method {} execution time: {} {}",
                invocation.getMethod().getName(),
                Duration.of(end - start, ChronoUnit.NANOS).get(anno.unit()),
                anno.unit().name());
            return proceed;
        }
    }
}
