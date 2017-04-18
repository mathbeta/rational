package com.mathbeta.rational.master.mapper;

import com.mathbeta.rational.common.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-4-15.
 */
public interface BaseMapper<Entity extends BaseEntity> {
    void save(Entity entity);

    void update(Entity entity);

    List<Entity> queryByParams(Map<String, Object> params);

    Entity queryById(String id);

    void deleteById(String id);

    int queryTotal(Map<String, Object> params);
}
