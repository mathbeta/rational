package com.mathbeta.rational.master.service.impl;

import com.mathbeta.rational.common.entity.Registry;
import com.mathbeta.rational.master.mapper.RegistryMapper;
import com.mathbeta.rational.master.service.IRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("registryService")
public class RegistryService extends BaseService<Registry, RegistryMapper> implements IRegistryService {
    @Autowired
    @Qualifier("registryMapper")
    private RegistryMapper registryMapper;

    @Override
    public RegistryMapper getMapper() {
        return this.registryMapper;
    }
}
