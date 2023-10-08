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
 * 1.对于get query string传参,servlet输入流中无需底层实现填充数据
 * 2.对于post [application/x-www-form-urlencoded,multipart/form-data,application/json],servlet输入流中需要由底层实现填充数据
 * 在方法参数中注入的request如果已经绑定了参数，则无法从输入流中获取数据;未绑定参数则可以获取到数据
 * 对于application/x-www-form-urlencoded且为POST时，一旦调用了getParameter则输入流中数据不再可用
 * 3.json类型需要从流中获取数据,其他类型已被绑定到request的parameter和part中无需从流中获取,也无法获取
 */
@RestController()
@RequestMapping("/requestReuse")
//用于开启url传参检测,实体对象检测需要单独开启
@Validated
@Slf4j
public class RequestReuseTestController {

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping("/formData")
    public String testFormData(@Validated User user, HttpServletRequest request,
                               @RequestPart("file") MultipartFile file) throws IOException {
        log.info("user:{},file:{}", user, file.getOriginalFilename());
        //body数据已被读取
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

    @RequestMapping("/formUrlEncoded")
    public String testFormUrl(@Validated User user, HttpServletRequest request) throws IOException {
        log.info("user:{}", user);
        //body数据已被读取
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

    @RequestMapping("/json1")
    public String testJson1(@Validated @RequestBody User user, HttpServletRequest request)
        throws IOException {
        log.info("user:{}", user);
        //可获取数据
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("request:{}", RequestUtils.getBodyAsString(request));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

    @RequestMapping("/json2")
    public String testJson2(HttpServletRequest request) throws IOException {
        //可获取数据
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
        //body无数据
        log.info("request:{}",
            StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
        log.info("params map:{}", mapper.writeValueAsString(request.getParameterMap()));
        return "ok";
    }

}
