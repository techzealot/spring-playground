package com.techzealot.spring.playground.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletRequest;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

public abstract class RequestUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getParameterMapAsString(ServletRequest request) {
        try {
            return objectMapper.writeValueAsString(request.getParameterMap());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getBodyAsString(ServletRequest request) {
        if (request instanceof JsonRequestWrapper wrapper) {
            return new String(wrapper.getBodyAsByteArray(), StandardCharsets.UTF_8);
        }
        return "[No Wrapper]";
    }

    public static String getInputAsString(ServletRequest request) {
        if (isJsonRequest(request)) {
            return getBodyAsString(request);
        } else {
            return getParameterMapAsString(request);
        }
    }

    public static boolean isJsonRequest(ServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE);
    }
}
