package com.mathbeta.rational.master.service.impl;

import com.mathbeta.rational.common.entity.Coordinator;
import com.mathbeta.rational.master.mapper.CoordinatorMapper;
import com.mathbeta.rational.master.service.ICoordinatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("coordinatorService")
public class CoordinatorService extends BaseService<Coordinator, CoordinatorMapper> implements ICoordinatorService {
    @Autowired
    @Qualifier("coordinatorMapper")
    private CoordinatorMapper coordinatorMapper;

    @Override
    public CoordinatorMapper getMapper() {
        return this.coordinatorMapper;
    }
}
