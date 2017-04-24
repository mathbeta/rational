package com.mathbeta.rational.minion.handler;

import com.mathbeta.rational.common.entity.Message;

/**
 * Created by Administrator on 17-4-18.
 */
public interface IDockerProcessHandler {
    Message handle() throws Exception;
}
