package com.mathbeta.rational.minion.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mathbeta.rational.common.utils.IdGenerator;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by 147458 on 2017/4/18.
 */
public class HostChecker {
    /**
     * 调用minion restful接口时需要在header中提供的认证码，每次启动minion时生成
     */
    public static String RESTFUL_AUTHORIZATION_CODE = IdGenerator.generateId();
    private static String ip = null;

    public static void check() throws Exception {
        checkDocker();
    }

    /**
     * 获取主机ip
     *
     * @throws Exception
     */
    public static String getIp() throws Exception {
        if (ip == null) {
            ip = InetAddress.getAllByName(null)[0].getHostAddress();
        }
        return ip;
    }

    public static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    mac = netInterface.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        if (sb.length() > 0) {
                            return sb.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取ip地址
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检查docker版本
     *
     * @throws Exception
     */
    private static void checkDocker() throws Exception {
        Map<String, List<String>> info = DockerUtil.getVersion();
        if (info.containsKey(ShellUtil.OUTPUT_STREAM_KEY)) {
            List<String> out = info.get(ShellUtil.OUTPUT_STREAM_KEY);
            String[] client = out.get(1).split("[ \t]");
            String[] server = out.get(9).split("[ \t]");
            System.out.println("docker version:");
            System.out.println("\tClient " + client[client.length - 1]);
            System.out.println("\tServer " + server[server.length - 1]);
        } else {
            throw new Exception("failed to get docker version" + JSON.toJSONString(info.get(ShellUtil.ERROR_STREAM_KEY)));
        }
    }

    /**
     * 获取主机配置信息
     *
     * @return
     */
    public static Map<String, Object> getConf() {
        Map<String, Object> conf = Maps.newHashMap();
        conf.put("cpus", Runtime.getRuntime().availableProcessors());
        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        conf.put("mem", bean.getTotalPhysicalMemorySize() / 1024 / 1024);
        return conf;
    }
}
