package com.wfc.starter.auth.exception;

import com.wfc.starter.auth.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 飞影
 * @create by 2020-03-07 18:45
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestResult<String> exceptionHandler(Exception e) {
        log.error("has exception", e);
        return RestResult.failure("系统出现异常！");
    }

    @ExceptionHandler(WfcAuthException.class)
    @ResponseBody
    public RestResult exceptionHandler(WfcAuthException e){
        return RestResult.failure(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public RestResult exceptionHandler(IllegalArgumentException e){
        return RestResult.failure(e.getMessage());
    }

}