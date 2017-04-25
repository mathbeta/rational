package com.mathbeta.rational.common.restful;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Created by Administrator on 17-4-24.
 */
public class AccessControlFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
//        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers","origin,content-type,accept,authorization");
//        containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials","true");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Max-Age", "1209600");
    }
}
