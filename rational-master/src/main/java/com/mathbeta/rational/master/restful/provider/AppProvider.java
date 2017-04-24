package com.mathbeta.rational.master.restful.provider;

import com.mathbeta.rational.common.entity.App;
import com.mathbeta.rational.common.utils.ServiceBeanContext;
import com.mathbeta.rational.master.service.impl.AppService;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "app", description = "app provider")
@Path("app")
public class AppProvider extends BaseProvider<App, AppService> {
    // assign networks


    // assign volumes


    // manage docker apps



    @Override
    protected AppService getService() {
        return (AppService) ServiceBeanContext.getInstance().getBean("appService");
    }
}
