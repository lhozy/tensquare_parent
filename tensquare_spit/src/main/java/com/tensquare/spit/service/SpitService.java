package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据上级ID查询吐槽列表
     *
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findSpitListPageByParentid(String parentid, int page, int size) {
        return spitDao.findByParentid(parentid, PageRequest.of(page - 1, size));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 新增
     *
     * @param spit
     */
    public void save(Spit spit) {
        spit.setId(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setShare(0);
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setState("1");
        if (spit.getParentid() != null && "" != spit.getParentid()) {
            //父节点的回复数+1
            /*Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");*/
            mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(spit.getParentid())), new Update().inc("comment", 1), "spit");
        }
        spitDao.save(spit);
    }

    /**
     * 更新
     *
     * @param spit
     */
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void delete(String id) {
        spitDao.deleteById(id);
    }

    public void thumbup(String id) {
        Spit spit = spitDao.findById(id).get();
        spit.setThumbup(spit.getThumbup() + 1);
        spitDao.save(spit);
    }
}
