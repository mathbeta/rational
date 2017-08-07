package com.mathbeta.rational.master.service.impl;


import com.google.common.collect.Maps;
import com.mathbeta.rational.common.entity.BaseEntity;
import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.common.entity.Page;
import com.mathbeta.rational.common.utils.IdGenerator;
import com.mathbeta.rational.master.mapper.BaseMapper;
import com.mathbeta.rational.master.service.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by 147458 on 2017/4/14.
 */
public abstract class BaseService<Entity extends BaseEntity, Mapper extends BaseMapper> implements IBaseService<Entity> {
    protected static Logger logger = LoggerFactory.getLogger(BaseService.class);

    /**
     * 获取具体的mapper
     *
     * @return
     */
    public abstract Mapper getMapper();

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    @Override
    public Message save(Entity entity) throws Exception {
        try {
            entity.setId(IdGenerator.generateId());
            this.getMapper().save(entity);
            return Message.build("save entity successfully", true, entity.getId());
        } catch (Exception e) {
            logger.error("failed to save entity", e);
            throw e;
        }
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public Message update(Entity entity) throws Exception {
        try {
            this.getMapper().update(entity);
            return Message.build("update entity successfully", true, entity.getId());
        } catch (Exception e) {
            logger.error("failed to update entity", e);
            throw e;
        }
    }

    /**
     * 根据查询条件查询所有记录
     *
     * @param params
     * @return
     */
    public List<Entity> queryByParams(Map<String, Object> params) throws Exception {
        try {
            if (params == null) {
                params = Maps.newHashMap();
            }
            return this.getMapper().queryByParams(params);
        } catch (Exception e) {
            logger.error("failed to query entities by params", e);
            throw e;
        }
    }

    /**
     * 根据查询条件查询所有记录数
     *
     * @param params
     * @return
     */
    public int countByParams(Map<String, Object> params) throws Exception {
        try {
            if (params == null) {
                params = Maps.newHashMap();
            }
            return this.getMapper().queryTotal(params);
        } catch (Exception e) {
            logger.error("failed to query entities by params", e);
            throw e;
        }
    }

    /**
     * 根据id查询单个记录
     *
     * @param id
     * @return
     */
    public Entity queryById(String id) throws Exception {
        try {
            return (Entity) this.getMapper().queryById(id);
        } catch (Exception e) {
            logger.error("failed to query entity by id", e);
            throw e;
        }
    }

    /**
     * 根据id删除记录，id用逗号分隔
     *
     * @param ids
     * @return
     */
    public Message deleteByIds(String ids) throws Exception {
        try {
            if (ids != null && !ids.isEmpty()) {
                String[] arr = ids.split(",");
                for (String id : arr) {
                    this.getMapper().deleteById(id);
                }
                return Message.build("delete by ids successfully", true, ids);
            }
            return Message.build("ids should not be empty", true, "");
        } catch (Exception e) {
            logger.error("failed to delete entities by id", e);
            throw e;
        }
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public Page<Entity> queryByPage(Page<Entity> page) throws Exception {
        try {
            int pageNum = page.getPageNum();
            int pageSize = page.getPageSize();
            page.setStart((pageNum - 1) * pageSize);
            page.setEnd(pageNum * pageSize);

            Map<String, Object> params = page.getParams();
            params.put("start", page.getStart());
            params.put("pageSize", page.getPageSize());
            int total = this.getMapper().queryTotal(params);
            List<Entity> rows = this.getMapper().queryByParams(params);
            page.setTotal(total);
            page.setRows(rows);
            return page;
        } catch (Exception e) {
            logger.error("failed to query entities by page", e);
            throw e;
        }
    }
}

