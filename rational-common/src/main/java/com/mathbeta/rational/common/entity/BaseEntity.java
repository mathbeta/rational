package com.mathbeta.rational.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 147458 on 2017/4/13.
 */
public class BaseEntity implements Serializable {
    private String id;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
