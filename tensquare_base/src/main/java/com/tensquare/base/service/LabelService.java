package com.tensquare.base.service;

import com.tensquare.base.dao.LabelRepository;
import com.tensquare.base.po.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author bruce
 * @date 2020/1/12 0012 - 下午 8:01
 */
@Service
public class LabelService {
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private IdWorker idWorker;

    public void save(Label label){
        label.setId(idWorker.nextId()+"");// 生成Id
        labelRepository.save(label);
    }

    public void update(Label label){

        labelRepository.save(label);
    }
    public void deleteLabelById(String id){
        labelRepository.deleteById(id);
    }
    public List<Label> findAll(){
        return labelRepository.findAll();
    }
    public Label findOne(String id){
         return labelRepository.findById(id).get();
    }

    /**
     * 根据名称(模糊)和状态(精准)查询
     * @param label
     * @return
     */
    public List<Label> findByCondition(Label label) {

        return labelRepository.findAll((root,query,cb)->{
            Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
            Predicate state = cb.equal(root.get("state"), label.getState());
            return cb.and(labelname,state);
        });
    }

    /**
     * 分页条件查询：根据名称(模糊)和状态(精准)查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> findByConditionPage(Label label, Integer page, Integer size) {
        if (null == page || page < 0) {
            page = 1;
        }
        if (null == size || size < 0) {
            size = 10;
        }
        Pageable pageable =PageRequest.of(page-1,size);
        return labelRepository.findAll((root, query, cb)->{
            Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
            Predicate state = cb.equal(root.get("state"), label.getState());
            return cb.and(labelname,state);
        },pageable);
    }
}
