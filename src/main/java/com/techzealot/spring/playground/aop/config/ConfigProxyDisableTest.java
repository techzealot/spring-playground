package com.techzealot.spring.playground.aop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * 关闭方法代理,必须采用参数注入形式,否则无法保证同一个Bean
 */
@Configuration(proxyBeanMethods = false)
public class ConfigProxyDisableTest {

    private static final Logger logger = LoggerFactory.getLogger(ConfigProxyDisableTest.class);

    @Bean
    public TestObject testObject1() {
        //should init only once
        logger.info("init TestObject1");
        return new TestObject();
    }

    /**
     * Autowired使用在方法上会使spring在参数注入时调用该方法，其调用时机早于PostConstruct
     */
    @Autowired
    public void check1() {
        TestObject o1 = testObject1();
        TestObject o2 = testObject1();
        Assert.isTrue(o1 != o2, "must be two bean");
    }

    @Configuration
    public static class Test {

        @Autowired
        public void inject1(TestObject testObject) {
            Assert.notNull(testObject, "must be not null");
        }
    }

    private static class TestObject {
    }
}
