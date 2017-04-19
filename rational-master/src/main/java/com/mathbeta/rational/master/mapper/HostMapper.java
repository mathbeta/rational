package com.mathbeta.rational.master.mapper;

import com.mathbeta.rational.common.entity.Host;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 17-4-15.
 */
public interface HostMapper extends BaseMapper<Host> {
    int updateHostRegistry(@Param("hostId") String hostId, @Param("registryId") String registryId);
}
