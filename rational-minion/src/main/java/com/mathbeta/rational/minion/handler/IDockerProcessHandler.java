package com.mathbeta.rational.minion.handler;

import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.common.utils.Constants;

/**
 * Created by Administrator on 17-4-18.
 */
public interface IDockerProcessHandler {
    Message handle(Constants.DockerProcessOp op) throws Exception;
}
