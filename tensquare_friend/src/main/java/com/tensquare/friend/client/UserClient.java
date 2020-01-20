package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {

    @PutMapping("/user/{userid}/{friendid}/{count}")
    public void updateFansAndFollowCount(@PathVariable String userid, @PathVariable String friendid, @PathVariable int count);
}
