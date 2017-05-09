package com.mathbeta.rational.common.restful;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * restful接口跨域访问设置
 * Created by Administrator on 17-4-24.
 */
public class AccessControlFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers","X-ACCESS_TOKEN, Access-Control-Allow-Origin, Authorization, Origin, x-requested-with, Content-Type, Content-Range, Content-Disposition, Content-Description");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials","true");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Max-Age", "1209600");
    }
}
