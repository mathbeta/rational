package com.mathbeta.rational.common.entity;

import java.io.Serializable;

/**
 * Created by 147458 on 2017/4/25.
 */
public class Minion implements Serializable {
    private int cpus;
    private int mem;
    private String auth;
    private String port;

    public int getCpus() {
        return cpus;
    }

    public void setCpus(int cpus) {
        this.cpus = cpus;
    }

    public int getMem() {
        return mem;
    }

    public void setMem(int mem) {
        this.mem = mem;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
