package com.techzealot.spring.playground.controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ReusedRequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest request) {
            //只能包装json类型,其他类型数据(如两种form类型)不能包装否则会导致提前读取request造成form数据无法读取(原因是wrapper模式是包装而非继承不能运行时多态,底层parameter和part等参数还是从底层流中获取)
            if (RequestUtils.isJsonRequest(request) && !(request instanceof JsonRequestWrapper)) {
                requestWrapper = new JsonRequestWrapper(request);
            }
        }
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}