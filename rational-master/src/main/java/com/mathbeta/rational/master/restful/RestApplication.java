package com.mathbeta.rational.master.restful;

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
    }
}
