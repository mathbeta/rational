package com.mathbeta.rational.common.entity;

/**
 * Created by 147458 on 2017/4/12.
 */
public class Network extends BaseEntity {
    private String name;
    private String clusterId;
    private String ipRange;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }
}
