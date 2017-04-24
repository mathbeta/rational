package com.mathbeta.rational.common.entity;

/**
 * Created by 147458 on 2017/4/12.
 */
public class Cluster extends BaseEntity {
    private String name;
    private String coordinatorId;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(String coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
