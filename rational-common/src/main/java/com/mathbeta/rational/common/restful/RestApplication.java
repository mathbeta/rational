package com.mathbeta.rational.common.restful;

import com.mathbeta.rational.common.utils.ConfigHelper;
import io.swagger.annotations.Api;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "/rest/*", description = "All the apis")
@ApplicationPath("/rest/*")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        register(RestConstraint.class);
        register(MultiPartFeature.class);
        register(RestJsonMapperProvider.class);
        register(JacksonFeature.class);
        // 如果没有显式设置跨域访问，则默认开启跨域访问
        if (!"false".equalsIgnoreCase(ConfigHelper.getJettyParameter("enable.cors"))) {
            register(AccessControlFilter.class);
        }
    }
}
