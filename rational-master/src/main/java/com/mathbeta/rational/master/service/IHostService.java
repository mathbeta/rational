package com.mathbeta.rational.master.service;

import com.mathbeta.rational.common.entity.Host;
import com.mathbeta.rational.common.entity.Message;

/**
 * Created by Administrator on 17-4-18.
 */
public interface IHostService extends IBaseService<Host> {
    Message updateHostRegistry(String hostId, String registryId) throws Exception;

    Message operateDocker(String op, String hostId) throws Exception;
}
