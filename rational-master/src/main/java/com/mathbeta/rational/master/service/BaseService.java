package com.mathbeta.rational.master.service;


import com.mathbeta.rational.common.entity.BaseEntity;
import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.common.entity.Page;
import com.mathbeta.rational.common.utils.IdGenerator;
import com.mathbeta.rational.master.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 147458 on 2017/4/14.
 */
public abstract class BaseService<Entity extends BaseEntity> {
    /**
     * 获取具体的mapper
     *
     * @return
     */
    public abstract BaseMapper getMapper();

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    public Message save(Entity entity) {
        entity.setId(IdGenerator.generateId());
        this.getMapper().save(entity);
        return Message.build("save entity successfully", true, entity.getId());
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public Message update(Entity entity) {
        this.getMapper().update(entity);
        return Message.build("update entity successfully", true, entity.getId());
    }

    /**
     * 根据查询条件查询所有记录
     *
     * @param params
     * @return
     */
    public List<Entity> queryByParams(Map<String, Object> params) {
        return this.getMapper().queryByParams(params);
    }

    /**
     * 根据id查询单个记录
     *
     * @param id
     * @return
     */
    public Entity queryById(String id) {
        return (Entity) this.getMapper().queryById(id);
    }

    /**
     * 根据id删除记录，id用逗号分隔
     *
     * @param ids
     * @return
     */
    public Message deleteByIds(String ids) {
        if (ids != null && !ids.isEmpty()) {
            String[] arr = ids.split(",");
            for (String id : arr) {
                this.getMapper().deleteById(id);
            }
            return Message.build("delete by ids successfully", true, ids);
        }
        return Message.build("ids shoud not be empty", true, "");
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public Page<Entity> queryByPage(Page<Entity> page) {
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
    }
}

