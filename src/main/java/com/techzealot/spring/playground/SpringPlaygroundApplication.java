package com.techzealot.spring.playground;

import com.techzealot.spring.playground.aop.aspectj.TestService;
import com.techzealot.spring.playground.aop.manual.TestManualService;
import com.techzealot.spring.playground.domain.ApplicationService;
import com.techzealot.spring.playground.domain.DomainService;
import com.techzealot.spring.playground.ioc.circular.AService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@SpringBootApplication()
@RestController
public class SpringPlaygroundApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private DomainService domainService;
    @Autowired
    private TestService testService;
    @Autowired
    private TestManualService testManualService;
    @Autowired
    private AService aService;

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./cglib");
        SpringApplication.run(SpringPlaygroundApplication.class, args);
    }

    @GetMapping("/test")
    public void testSql() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from users");
        System.out.println(maps);
        applicationService.callTestA();
        applicationService.callTestB();
    }

    @GetMapping("/testAop")
    public void testAop() throws InterruptedException {
        //内部调用aop失效
        testService.execute();
        testService.innerCall();
    }

    @GetMapping("/testManualAop")
    public void testManualAop() throws InterruptedException {
        //内部调用aop失效
        testManualService.execute();
        testManualService.innerCall();
    }

    @GetMapping("/testLazy")
    public void testLazy() {
        aService.runLazy();
    }

}
