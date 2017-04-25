package com.mathbeta.rational.minion.restful.provider;

import com.mathbeta.rational.common.entity.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 17-4-24.
 */
@Api(value = "docker", description = "docker provider")
@Path("docker")
public class DockerProvider {
    @ApiOperation(value = "operate docker progress", notes = "operate docker progress", response = Message.class)
    @POST
    @Path("{op}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message op(@PathParam("op") String op, String body) {
        System.out.println(op + " " + body);
        return Message.build("op docker successfully", true, body);
    }
}
