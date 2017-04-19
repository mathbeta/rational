package com.mathbeta.rational.minion.handler;

import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.minion.utils.ShellUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 17-4-18.
 */
public abstract class BaseDockerProcessHandler implements IDockerProcessHandler {
    private static Logger logger = LoggerFactory.getLogger(BaseDockerProcessHandler.class);

    /**
     * 执行一个命令
     *
     * @param cmd
     * @return
     */
    public Message exec(String cmd) {
        try {
            ShellUtil.exec(cmd);
            return Message.build("exec cmd [" + cmd + "] successfully", true, "");
        } catch (Exception e) {
            logger.error("failed to exec cmd: " + cmd, e);
            return Message.build(e.getMessage(), false, e);
        }
    }
}
