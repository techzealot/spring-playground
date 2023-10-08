package com.techzealot.spring.playground.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techzealot.spring.playground.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 在方法参数中注入的request如果已经绑定了参数，则无法从输入流中获取数据；未绑定参数则可以获取到数据
 * 对于application/x-www-form-urlencoded且为POST时，一旦调用了getParameter则输入流中数据不再可用
 */
@RestController()
@RequestMapping("/validation")
//用于开启query string传参校验,实体对象检测需要单独开启
@Validated
@Slf4j
public class ValidationTestController {

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping("/formData")
    public String testFormData(@Validated User user, HttpServletRequest request,
                               @RequestPart("file") MultipartFile file) throws IOException {
        log.info("user:{},file:{}", user, file.getOriginalFilename());
        //读不到
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

    @RequestMapping("/formUrlEncoded")
    public String testFormUrl(@Validated User user, HttpServletRequest request) throws IOException {
        log.info("user:{}", user);
        //get可以读到但不符合规范,post由于servlet规范无法读取
        //name=tom&age=122
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

    @RequestMapping("/json1")
    public String testJson1(@Validated @RequestBody User user, HttpServletRequest request)
        throws IOException {
        log.info("user:{}", user);
        //此处如果不做特殊处理是无法从request中获取数据的，因为在spring绑定参数时已经使用了输入流
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

    @RequestMapping("/json2")
    public String testJson2(HttpServletRequest request) throws IOException {
        System.out.println(1);
        //此处由于未使用输入流，可以获取数据
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

    @RequestMapping("/param")
    public String testParam(@NotNull(message = "name不能为空") String name,
                            @Range(min = 0, max = 120) int age
        , HttpServletRequest request) throws IOException {
        log.info("name:{},age:{}", name, age);
        //request中读不到数据
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

}
