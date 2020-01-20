package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bruce
 * @date 2020/1/20 0020 - 上午 1:04
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    public NoFriend findByUseridAndFriendid(String userid, String friendid);


}
