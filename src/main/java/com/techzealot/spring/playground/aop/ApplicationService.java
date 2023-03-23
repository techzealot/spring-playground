package com.techzealot.spring.playground.aop;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    @Autowired
    private DomainService domainService;

    /**
     * 可以通过如下方式获取代理对象持有的原始对象
     * @return
     * @throws Exception
     */
    public DomainService getTargetDomainService() throws Exception {
        return (DomainService) ((Advised)domainService).getTargetSource().getTarget();
    }

    public void callTestA() {
        //testA事务生效，但testB事务失效
        domainService.testA();
    }

    public void callTestB() {
        //testB事务生效
        domainService.testB();
    }
}
