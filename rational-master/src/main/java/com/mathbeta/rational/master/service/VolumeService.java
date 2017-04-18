package com.mathbeta.rational.master.service;

import com.mathbeta.rational.common.entity.Volume;
import com.mathbeta.rational.master.mapper.BaseMapper;
import com.mathbeta.rational.master.mapper.VolumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("volumeService")
public class VolumeService extends BaseService<Volume> {
    @Autowired
    @Qualifier("volumeMapper")
    private VolumeMapper volumeMapper;

    @Override
    public BaseMapper getMapper() {
        return this.volumeMapper;
    }
}
