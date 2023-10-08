package com.techzealot.spring.playground.controller;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.util.StreamUtils;

/**
 * 支持重复读取application/json类型的请求数据
 * 不能包装其他类型，否则会导致底层流中数据不可再读，造成参数或文件等数据丢失
 * 不能完美覆盖所有场景的原因:
 * 1.wrapper是包装模式，其他方法使用被包装对象的方法实现功能而非重写后的方法
 * 2.仅重写了部分方法，其他类型获取数据时还是使用的底层流，仍然不能多次读取
 */
public class JsonRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public JsonRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return bais.available() <= 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public byte[] getBodyAsByteArray() {
        return body;
    }
}
