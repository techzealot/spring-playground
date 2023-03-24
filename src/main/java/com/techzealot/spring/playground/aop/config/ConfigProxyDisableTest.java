package com.techzealot.spring.playground.aop.config;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 关闭方法代理,必须采用构造器注入形式,否则无法保证同一个Bean
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
     * @return
     */
    @Bean
    public String check1() {
        TestObject o1 = testObject1();
        TestObject o2 = testObject1();
        Asserts.check(o1 != o2, "must be two bean");
        return "";
    }

    @Bean
    public String inject1(TestObject testObject) {
        Asserts.notNull(testObject, "must be not null");
        return "";
    }

    private static class TestObject {
    }
}
