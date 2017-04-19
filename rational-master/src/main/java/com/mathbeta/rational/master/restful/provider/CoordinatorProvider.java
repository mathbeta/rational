package com.mathbeta.rational.master.restful.provider;

import com.mathbeta.rational.common.entity.Coordinator;
import com.mathbeta.rational.common.utils.ServiceBeanContext;
import com.mathbeta.rational.master.service.impl.CoordinatorService;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "coordinator", description = "coordinator provider")
@Path("coordinator")
public class CoordinatorProvider extends BaseProvider<Coordinator, CoordinatorService> {
    @Override
    protected CoordinatorService getService() {
        return (CoordinatorService) ServiceBeanContext.getInstance().getBean("coordinatorService");
    }
}
