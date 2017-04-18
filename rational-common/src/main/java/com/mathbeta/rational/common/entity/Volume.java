package com.mathbeta.rational.common.entity;

/**
 * Created by 147458 on 2017/4/12.
 */
public class Volume extends BaseEntity {
    private String name;
    private String url;
    private String containerPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContainerPath() {
        return containerPath;
    }

    public void setContainerPath(String containerPath) {
        this.containerPath = containerPath;
    }
}
