package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
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
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserClient userClient;

    public int addFriend(String userid, String friendid) {
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (friend != null) {
            return 0;//0已存在不能重复添加,1新增
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
    public int addNoFriend(String userid, String friendid){
        //0已存在不能重复添加,1新增
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if (noFriend != null) {
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        friendDao.updatIslike("88", userid, friendid);//标识为删除状态88
        userClient.updateFansAndFollowCount(userid, friendid, -1);//更改粉丝和关注
    }
}
