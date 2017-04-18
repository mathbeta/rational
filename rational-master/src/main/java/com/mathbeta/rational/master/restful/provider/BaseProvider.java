package com.mathbeta.rational.master.restful.provider;

import com.google.common.collect.Lists;
import com.mathbeta.rational.common.entity.BaseEntity;
import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.common.entity.Page;
import com.mathbeta.rational.master.service.BaseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by 147458 on 2017/4/13.
 */
public abstract class BaseProvider<Entity extends BaseEntity> {
    private static Logger logger = LoggerFactory.getLogger(BaseProvider.class);

    /**
     * 获取具体的业务逻辑实现service
     *
     * @return
     */
    protected abstract BaseService getService();

    @ApiOperation(value = "save entity in db", notes = "save entity", response = Message.class)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message save(@ApiParam(value = "an entity json string", required = true) Entity entity) {
        try {
            return this.getService().save(entity);
        } catch (Exception e) {
            logger.error("failed to save entity", e);
            return Message.build("failed to update", false, e);
        }
    }

    @ApiOperation(value = "update entity in db", notes = "update entity", response = Message.class)
    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message update(@ApiParam(value = "an entity json string", required = true) Entity entity) {
        try {
            return this.getService().update(entity);
        } catch (Exception e) {
            logger.error("failed to update entity", e);
            return Message.build("failed to update", false, e);
        }
    }

    @ApiOperation(value = "query entities from db", notes = "query entities", response = List.class)
    @POST
    @Path("queryByParams")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Entity> queryByParams(@ApiParam(value = "a param json string", required = true) Map<String, Object> params) {
        try {
            return (List<Entity>) this.getService().queryByParams(params);
        } catch (Exception e) {
            logger.error("failed to query all entity", e);
            return Lists.newArrayList();
        }
    }

    @ApiOperation(value = "query entities from db by page", notes = "query entities by page", response = Page.class)
    @POST
    @Path("queryByPage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Page queryByPage(@ApiParam(value = "a page json string", required = true) Page page) {
        try {
            return this.getService().queryByPage(page);
        } catch (Exception e) {
            logger.error("failed to query all entity", e);
            return page;
        }
    }

    @ApiOperation(value = "query entity from db by id", notes = "query entity by id", response = BaseEntity.class)
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Entity queryById(@ApiParam(value = "an id", required = true) @PathParam("id") String id) {
        try {
            return (Entity) this.getService().queryById(id);
        } catch (Exception e) {
            logger.error("failed to query entity by id", e);
            return null;
        }
    }

    @ApiOperation(value = "delete entities in db by ids", notes = "delete entities by ids", response = Message.class)
    @DELETE
    @Path("{ids}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message deleteByIds(@ApiParam(value = "ids of comma separated id", required = true) @PathParam("ids") String ids) {
        try {
            return this.getService().deleteByIds(ids);
        } catch (Exception e) {
            logger.error("failed to delete entities by id", e);
            return Message.build("failed to delete by ids", false, e);
        }
    }
}
