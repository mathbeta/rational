package com.mathbeta.rational.master.service;

import com.mathbeta.rational.common.entity.Host;
import com.mathbeta.rational.master.mapper.BaseMapper;
import com.mathbeta.rational.master.mapper.HostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("hostService")
public class HostService extends BaseService<Host> {
    @Autowired
    @Qualifier("hostMapper")
    private HostMapper hostMapper;

    @Override
    public BaseMapper getMapper() {
        return this.hostMapper;
    }
}
