package com.mathbeta.rational.master.restful.provider;

import com.mathbeta.rational.common.entity.Registry;
import com.mathbeta.rational.common.utils.ServiceBeanContext;
import com.mathbeta.rational.master.service.BaseService;
import com.mathbeta.rational.master.service.RegistryService;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "registry", description = "registry provider")
@Path("registry")
public class RegistryProvider extends BaseProvider<Registry> {
    @Override
    protected BaseService getService() {
        return (RegistryService) ServiceBeanContext.getInstance().getBean("registryService");
    }
}
