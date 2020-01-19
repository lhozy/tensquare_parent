package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bruce
 * @date 2020/1/20 0020 - 上午 12:15
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;

    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable("friendid") String friendid,@PathVariable("type") String type){
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (null == claims){
            return new Result(false,StatusCode.ERROR,"权限不足");
        }
        if (type != null) {
            if (type.equals("1")){
                //添加好友
                String userId = claims.getId();
                int flag = friendService.addFriend(userId,friendid);
                if (flag == 0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加好友");
                }
                if (flag == 1){
                    return new Result(true,StatusCode.OK,"添加好友成功");
                }
            }else if (type.equals("2")){
                //删除好友
            }
            return new Result(false,StatusCode.ERROR,"参数异常");
        }else {
            return new Result(false,StatusCode.ERROR,"参数异常");
        }

    }
}
