package com.mathbeta.rational.master.service;

import com.mathbeta.rational.common.entity.Cluster;
import com.mathbeta.rational.master.mapper.BaseMapper;
import com.mathbeta.rational.master.mapper.ClusterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("clusterService")
public class ClusterService extends BaseService<Cluster> {
    @Autowired
    @Qualifier("clusterMapper")
    private ClusterMapper clusterMapper;

    @Override
    public BaseMapper getMapper() {
        return this.clusterMapper;
    }
}
