package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.NoFriendDao;
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
    @Autowired
    private UserClient userClient;


    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable("friendid") String friendid,@PathVariable("type") String type){
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (null == claims){
            return new Result(false,StatusCode.ERROR,"权限不足");
        }
        if (type != null) {
            String userId = claims.getId();
            if (type.equals("1")){
                //添加好友
                int flag = friendService.addFriend(userId,friendid);
                if (flag == 0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加");
                }
                if (flag == 1){
                    userClient.updateFansAndFollowCount(userId,friendid,1);
                    return new Result(true,StatusCode.OK,"添加成功");
                }
            }else if (type.equals("2")){
                //添加非好友
                int flag = friendService.addNoFriend(userId, friendid);
                if (flag == 0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加");
                }
                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }

    }

    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid) {
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (null == claims) {
            return new Result(false, StatusCode.ERROR, "失败");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid, friendid);
        return new Result(true, StatusCode.OK, "成功");
    }
}
