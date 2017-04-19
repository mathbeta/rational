package com.mathbeta.rational.master.service.impl;

import com.mathbeta.rational.common.entity.App;
import com.mathbeta.rational.master.mapper.AppMapper;
import com.mathbeta.rational.master.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("appService")
public class AppService extends BaseService<App, AppMapper> implements IAppService {
    @Autowired
    @Qualifier("appMapper")
    private AppMapper appMapper;

    @Override
    public AppMapper getMapper() {
        return this.appMapper;
    }
}
