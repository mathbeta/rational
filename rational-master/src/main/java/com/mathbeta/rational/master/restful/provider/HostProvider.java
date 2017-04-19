package com.mathbeta.rational.master.restful.provider;

import com.mathbeta.rational.common.entity.Host;
import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.common.utils.ServiceBeanContext;
import com.mathbeta.rational.master.service.impl.HostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "host", description = "host provider")
@Path("host")
public class HostProvider extends BaseProvider<Host, HostService> {
    // assign registry
    @ApiOperation(value = "set default docker registry for the host with given id", notes = "set default docker registry", response = Message.class)
    @POST
    @Path("registry/{hostId}/{registryId}")
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message registry(@ApiParam(value = "host id", required = true) @PathParam("hostId") String hostId, @ApiParam(value = "registry id", required = true) @PathParam("registryId") String registryId) {
        try {
            return this.getService().updateHostRegistry(hostId, registryId);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.build(e.getMessage(), false, e);
        }
    }

    // manage docker process
    @ApiOperation(value = "operate docker process, such as start, stop, kill, pause, unpause, restart, etc.", notes = "operate docker process", response = Message.class)
    @POST
    @Path("docker/{op}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message operateDocker(@ApiParam(value = "operate name", required = true) @PathParam("op") String op) {
        try {
            return this.getService().operateDocker(op);
        } catch (Exception e) {
            e.printStackTrace();
            return Message.build(e.getMessage(), false, e);
        }
    }


    @Override
    protected HostService getService() {
        return (HostService) ServiceBeanContext.getInstance().getBean("hostService");
    }
}
