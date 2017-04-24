package com.mathbeta.rational.minion.handler;

import com.mathbeta.rational.common.entity.Message;

/**
 * Created by Administrator on 17-4-18.
 */
public class DockerProcessStartHandler extends BaseDockerProcessHandler {
    @Override
    public Message handle() throws Exception {
        return Message.build("started", true, Math.random());
    }
}
