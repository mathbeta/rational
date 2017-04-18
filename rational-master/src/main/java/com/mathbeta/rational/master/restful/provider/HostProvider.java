package com.mathbeta.rational.master.restful.provider;

import com.mathbeta.rational.common.entity.Host;
import com.mathbeta.rational.common.utils.ServiceBeanContext;
import com.mathbeta.rational.master.service.BaseService;
import com.mathbeta.rational.master.service.HostService;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "host", description = "host provider")
@Path("host")
public class HostProvider extends BaseProvider<Host> {
    // assign registry


    // manage docker process


    @Override
    protected BaseService getService() {
        return (HostService) ServiceBeanContext.getInstance().getBean("hostService");
    }
}
