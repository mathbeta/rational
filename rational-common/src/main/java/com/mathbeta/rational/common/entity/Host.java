package com.mathbeta.rational.common.entity;

/**
 * Created by 147458 on 2017/4/12.
 */
public class Host extends BaseEntity {
    private String clusterId;
    private String ip;
    private int port;
    private String hostname;
    private String userName;
    private String passwd;
    private boolean daemonStarted;
    private Registry registry;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean isDaemonStarted() {
        return daemonStarted;
    }

    public void setDaemonStarted(boolean daemonStarted) {
        this.daemonStarted = daemonStarted;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }
}
