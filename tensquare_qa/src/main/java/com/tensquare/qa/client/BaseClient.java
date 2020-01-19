package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author bruce
 * @date 2020/1/19 0019 - 下午 10:07
 */
@FeignClient(value = "tensquare-base")
public interface BaseClient {
    @GetMapping("/label/{id}")
//    @RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);
}
