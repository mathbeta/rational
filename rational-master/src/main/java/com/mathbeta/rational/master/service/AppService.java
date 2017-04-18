package com.mathbeta.rational.master.service;

import com.mathbeta.rational.common.entity.App;
import com.mathbeta.rational.master.mapper.BaseMapper;
import com.mathbeta.rational.master.mapper.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("appService")
public class AppService extends BaseService<App> {
    @Autowired
    @Qualifier("appMapper")
    private AppMapper appMapper;

    @Override
    public BaseMapper getMapper() {
        return this.appMapper;
    }
}
