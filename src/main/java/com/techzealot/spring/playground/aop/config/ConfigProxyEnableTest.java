package com.techzealot.spring.playground.aop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * 默认代理内部方法的调用，保证单例Bean
 */
@Configuration(proxyBeanMethods = true)
public class ConfigProxyEnableTest {
    private static final Logger logger = LoggerFactory.getLogger(ConfigProxyEnableTest.class);

    @Bean
    public TestObject testObject() {
        //should init only once
        logger.info("init TestObject");
        return new TestObject();
    }

    /**
     * @return
     */
    @Bean
    public String check() {
        TestObject o1 = testObject();
        TestObject o2 = testObject();
        Assert.isTrue(o1 == o2, "must be same bean");
        return "";
    }

    @Bean
    public String inject(TestObject testObject) {
        Assert.notNull(testObject, "must be not null");
        return "";
    }

    private static class TestObject {
    }
}
