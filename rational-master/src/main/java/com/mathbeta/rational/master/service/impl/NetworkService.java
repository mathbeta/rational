package com.mathbeta.rational.master.service.impl;

import com.mathbeta.rational.common.entity.Network;
import com.mathbeta.rational.master.mapper.NetworkMapper;
import com.mathbeta.rational.master.service.INetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("networkService")
public class NetworkService extends BaseService<Network, NetworkMapper> implements INetworkService {
    @Autowired
    @Qualifier("networkMapper")
    private NetworkMapper networkMapper;

    @Override
    public NetworkMapper getMapper() {
        return this.networkMapper;
    }
}
