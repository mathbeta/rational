package com.mathbeta.rational.master.restful.provider;

import com.mathbeta.rational.common.entity.Volume;
import com.mathbeta.rational.common.utils.ServiceBeanContext;
import com.mathbeta.rational.master.service.impl.VolumeService;
import io.swagger.annotations.Api;

import javax.ws.rs.Path;

/**
 * Created by 147458 on 2017/4/12.
 */
@Api(value = "volume", description = "volume provider")
@Path("volume")
public class VolumeProvider extends BaseProvider<Volume, VolumeService> {
    @Override
    protected VolumeService getService() {
        return (VolumeService) ServiceBeanContext.getInstance().getBean("volumeService");
    }
}
