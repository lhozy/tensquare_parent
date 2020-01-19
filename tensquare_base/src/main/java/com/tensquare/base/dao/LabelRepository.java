package com.tensquare.base.dao;

import com.tensquare.base.po.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author bruce
 * @date 2020/1/12 0012 - 下午 7:59
 */
public interface LabelRepository extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {

}
