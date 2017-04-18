package com.mathbeta.rational.master.restful.provider;

import com.mathbeta.rational.common.entity.Network;
import com.mathbeta.rational.common.utils.ServiceBeanContext;
import com.mathbeta.rational.master.service.BaseService;
import com.mathbeta.rational.master.service.NetworkService;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "network", description = "network provider")
@Path("network")
public class NetworkProvider extends BaseProvider<Network> {
    @Override
    protected BaseService getService() {
        return (NetworkService) ServiceBeanContext.getInstance().getBean("networkService");
    }
}
