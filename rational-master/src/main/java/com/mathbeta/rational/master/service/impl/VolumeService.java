package com.mathbeta.rational.master.service.impl;

import com.mathbeta.rational.common.entity.Volume;
import com.mathbeta.rational.master.mapper.VolumeMapper;
import com.mathbeta.rational.master.service.IVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 17-4-15.
 */
@Service("volumeService")
public class VolumeService extends BaseService<Volume, VolumeMapper> implements IVolumeService {
    @Autowired
    @Qualifier("volumeMapper")
    private VolumeMapper volumeMapper;

    @Override
    public VolumeMapper getMapper() {
        return this.volumeMapper;
    }
}
