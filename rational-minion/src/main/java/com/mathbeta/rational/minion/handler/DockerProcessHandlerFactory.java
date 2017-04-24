package com.mathbeta.rational.minion.handler;

import com.google.common.collect.Maps;
import com.mathbeta.rational.common.utils.Constants;

import java.util.Map;

/**
 * Created by Administrator on 17-4-18.
 */
public class DockerProcessHandlerFactory {
    private static DockerProcessHandlerFactory factory;
    private Map<Constants.DockerProcessOp, IDockerProcessHandler> handlerMap = Maps.newHashMap();

    private DockerProcessHandlerFactory() {
        handlerMap.put(Constants.DockerProcessOp.START, new DockerProcessStartHandler());
        handlerMap.put(Constants.DockerProcessOp.STOP, new DockerProcessStopHandler());
        handlerMap.put(Constants.DockerProcessOp.KILL, new DockerProcessKillHandler());
        handlerMap.put(Constants.DockerProcessOp.PAUSE, new DockerProcessPauseHandler());
        handlerMap.put(Constants.DockerProcessOp.UNPAUSE, new DockerProcessUnpauseHandler());
        handlerMap.put(Constants.DockerProcessOp.RESTART, new DockerProcessRestartHandler());
    }

    public static DockerProcessHandlerFactory getFactory() {
        if (factory == null) {
            synchronized (DockerProcessHandlerFactory.class) {
                if (factory == null) {
                    factory = new DockerProcessHandlerFactory();
                }
            }
        }
        return factory;
    }

    public IDockerProcessHandler getHandler(Constants.DockerProcessOp op) {
        return handlerMap.get(op);
    }
}
