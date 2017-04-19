package com.mathbeta.rational.master.service;

import com.mathbeta.rational.common.entity.BaseEntity;
import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.common.entity.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-4-18.
 */
public interface IBaseService<Entity extends BaseEntity> {
    Message save(Entity entity) throws Exception;

    Message update(Entity entity) throws Exception;

    List<Entity> queryByParams(Map<String, Object> params) throws Exception;

    Entity queryById(String id) throws Exception;

    Message deleteByIds(String ids) throws Exception;

    Page<Entity> queryByPage(Page<Entity> page) throws Exception;
}
