package com.techzealot.spring.playground.ioc.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class AService {

    private BService bService;

    /**
     * 非Lazy模式注入LazyBean会导致延迟失效,未达到lazy意图的使用方式
     */
    @Autowired
    private CService cService;

    /**
     * Lazy模式注入LazyBean,bean会在使用时初始化,正确使用方式
     */
    @Autowired
    @Lazy
    private DService dService;


    /**
     * lazy模式注入非LazyBean,不会达到使用时初始化的效果但是可以打破循环依赖，将这种bean创建推迟到靠后的阶段
     * Lazy仅标注在使用处会在beanFactory.preInstantiateSingletons()阶段触发bean创建
     * BService本身并不是延迟初始化bean
     *
     * @param bService
     */
    public AService(@Lazy BService bService) {
        System.out.println("init AService");
        this.bService = bService;
    }

    /**
     * 会在运行时执行该方法时创建DService单例bean
     */
    public void runLazy() {
        dService.test();
    }
}
