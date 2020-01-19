package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bruce
 * @date 2020/1/20 0020 - 上午 12:16
 */
@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    public int addFriend(String userid, String friendid) {
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (friend != null){
            return 0;//重复添加
        }
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //islike=1：相互是好友；=0单相好友
        if (friendDao.findByUseridAndFriendid(friendid,userid)!=null){
            friendDao.updatIslike("1",friendid,userid);
            friendDao.updatIslike("1",userid,friendid);
        }
        return 1;
    }
}
