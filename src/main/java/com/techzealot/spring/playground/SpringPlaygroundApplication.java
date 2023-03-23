package com.techzealot.spring.playground;

import com.techzealot.spring.playground.aop.ApplicationService;
import com.techzealot.spring.playground.aop.DomainService;
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

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "./cglib");
        SpringApplication.run(SpringPlaygroundApplication.class, args);
    }

    @GetMapping("/test")
    public void testSql(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from users");
        System.out.println(maps);
        applicationService.callTestA();
        applicationService.callTestB();
    }

}
