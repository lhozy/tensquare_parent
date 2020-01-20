package com.tensquare.qa.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @author bruce
 * @date 2020/1/20 0020 - 下午 8:48
 */
@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"触发熔断器");
    }
}
