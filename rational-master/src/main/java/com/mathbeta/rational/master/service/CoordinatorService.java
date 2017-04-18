package com.mathbeta.rational.master.service;

import com.mathbeta.rational.common.entity.Coordinator;
import com.mathbeta.rational.master.mapper.BaseMapper;
import com.mathbeta.rational.master.mapper.CoordinatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("coordinatorService")
public class CoordinatorService extends BaseService<Coordinator> {
    @Autowired
    @Qualifier("coordinatorMapper")
    private CoordinatorMapper coordinatorMapper;

    @Override
    public BaseMapper getMapper() {
        return this.coordinatorMapper;
    }
}
