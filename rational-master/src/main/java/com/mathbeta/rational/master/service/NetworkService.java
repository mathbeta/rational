package com.mathbeta.rational.master.service;

import com.mathbeta.rational.common.entity.Network;
import com.mathbeta.rational.master.mapper.BaseMapper;
import com.mathbeta.rational.master.mapper.NetworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("networkService")
public class NetworkService extends BaseService<Network> {
    @Autowired
    @Qualifier("networkMapper")
    private NetworkMapper networkMapper;

    @Override
    public BaseMapper getMapper() {
        return this.networkMapper;
    }
}
