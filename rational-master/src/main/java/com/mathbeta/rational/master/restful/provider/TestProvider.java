package com.mathbeta.rational.master.restful.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by 147458 on 2017/4/20.
 */
@Path("test")
public class TestProvider {
    private static Logger logger = LoggerFactory.getLogger(TestProvider.class);
    @GET
    @Path("{name}")
    public String hello(@PathParam("name") String name) {
        logger.debug("receive name: {}", name);
        logger.error("test error {}", name);
        return "hello, " + name;
    }
}
