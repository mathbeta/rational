package com.mathbeta.rational.master.service.impl;

import com.mathbeta.rational.common.entity.Host;
import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.common.entity.Registry;
import com.mathbeta.rational.master.mapper.HostMapper;
import com.mathbeta.rational.master.mapper.RegistryMapper;
import com.mathbeta.rational.master.service.IHostService;
import com.mathbeta.rational.master.utils.CommandUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("hostService")
public class HostService extends BaseService<Host, HostMapper> implements IHostService {
    @Autowired
    @Qualifier("hostMapper")
    private HostMapper hostMapper;

    @Autowired
    @Qualifier("registryMapper")
    private RegistryMapper registryMapper;

    @Override
    public HostMapper getMapper() {
        return this.hostMapper;
    }

    public Message updateHostRegistry(String hostId, String registryId) throws Exception {
        int count = hostMapper.updateHostRegistry(hostId, registryId);
        if (count == 1) {
            Host host = hostMapper.queryById(hostId);
            Registry registry = registryMapper.queryById(registryId);
            // set registry info for docker process

            return Message.build("update registry for host successfully", true, "");
        } else {
            throw new Exception("failed to update host registry, update db returned: " + count);
        }
    }

    @Override
    public Message operateDocker(String op, String hostId) throws Exception {
        Host host = hostMapper.queryById(hostId);
        String response = CommandUtil.operateDocker(op, host.getIp());
        return Message.build(response, true, "");
    }
}
