package com.mathbeta.rational.master.restful.provider;

import com.mathbeta.rational.common.entity.Cluster;
import com.mathbeta.rational.common.utils.ServiceBeanContext;
import com.mathbeta.rational.master.service.BaseService;
import com.mathbeta.rational.master.service.ClusterService;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "cluster", description = "cluster provider")
@Path("cluster")
public class ClusterProvider extends BaseProvider<Cluster> {
    @Override
    protected BaseService getService() {
        return (ClusterService) ServiceBeanContext.getInstance().getBean("clusterService");
    }
}
