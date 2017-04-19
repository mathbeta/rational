package com.mathbeta.rational.common.utils;

/**
 * Created by Administrator on 17-4-18.
 */
public class Constants {
    public enum DockerProcessOp {
        START("start"),
        STOP("stop"),
        KILL("kill"),
        PAUSE("pause"),
        UNPAUSE("unpause"),
        RESTART("restart")
        ;

        private String op;
        DockerProcessOp(){};
        DockerProcessOp(String op) {
          this.op=op;
        };
    }
}
