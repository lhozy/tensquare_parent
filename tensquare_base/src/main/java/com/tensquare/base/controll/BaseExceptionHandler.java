package com.tensquare.base.controll;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理
 * @author bruce
 * @date 2020/1/12 0012 - 下午 9:54
 */
@RestControllerAdvice //controller增强
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class) //捕获异常
    public Result exception(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
