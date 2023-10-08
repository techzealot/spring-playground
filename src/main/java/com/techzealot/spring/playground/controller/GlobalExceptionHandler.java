package com.techzealot.spring.playground.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 对于application/json类型请求需要从body中获取，需要处理流只能能读一次问题
 * 对于非application/json类型请求,可使用request.getParameterMap()获取参数,request.getParts()获取文件
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /***
     * 可能出现的未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult<?> handle(Exception e, ServletRequest request) {
        log.error("input:{}", RequestUtils.getInputAsString(request));
        log.error(e.getMessage(), e);
        return ApiResult.error(e.getMessage());
    }

    /***
     * 参数异常:ConstraintViolationException()
     * 用于处理类似url传参[http://xxx?age=30&name=tom]请求中age和name的校验引发的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> urlParametersExceptionHandle(ConstraintViolationException e,
                                                     ServletRequest request)
        throws JsonProcessingException {
        //可以获取到原始参数
        log.error("input:{}", RequestUtils.getInputAsString(request));
        log.error(e.getMessage(), e);
        //收集所有错误信息,可能有多个不满足条件的字段
        List<String> errorMsg = e.getConstraintViolations()
            .stream().map(s -> s.getMessage()).collect(Collectors.toList());
        return ApiResult.error(BaseResultEnum.INVALID_PARAMETER, errorMsg.toString());
    }

    /***
     * 参数异常: MethodArgumentNotValidException
     * MethodArgumentNotValidException: 用于处理请求参数为实体类时校验引发的异常 Content-Type为application/json
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> jsonExceptionHandle(MethodArgumentNotValidException e,
                                            ServletRequest request) throws JsonProcessingException {
        log.error("input:{}", RequestUtils.getInputAsString(request));
        log.error(e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        //收集所有错误信息
        List<String> errorMsg = bindingResult.getFieldErrors().stream()
            .map(s -> s.getDefaultMessage()).collect(Collectors.toList());
        return ApiResult.error(BaseResultEnum.INVALID_PARAMETER, errorMsg.toString());
    }

    /***
     * 参数异常: BindException
     * BindException: 用于处理请求参数为实体类时校验引发的异常 Content-Type为application/x-www-form-urlencoded或multipart/form-data
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> formExceptionHandle(BindException e, ServletRequest request)
        throws JsonProcessingException {
        log.error("input:{}", RequestUtils.getInputAsString(request));
        log.error(e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        //收集所有错误信息
        List<String> errorMsg = bindingResult.getFieldErrors().stream()
            .map(s -> s.getDefaultMessage()).collect(Collectors.toList());
        return ApiResult.error(BaseResultEnum.INVALID_PARAMETER, errorMsg.toString());

    }

    /***
     * 自定义异常:自定义异常一般不要设置为ERROR级别,因为我们用自定义的异常主要是为了辅助我们处理业务逻辑
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseBody
    public ApiResult<?> handleBusinessException(BusinessException e, ServletRequest request)
        throws JsonProcessingException {
        log.error("input:{}", RequestUtils.getInputAsString(request));
        log.warn(e.getMessage(), e);
        return ApiResult.error(BaseResultEnum.INTERNAL_ERROR, e.getMessage());
    }
}
