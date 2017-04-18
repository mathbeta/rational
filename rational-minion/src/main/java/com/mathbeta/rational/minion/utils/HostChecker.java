package com.mathbeta.rational.minion.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * Created by 147458 on 2017/4/18.
 */
public class HostChecker {
    public static void check() throws Exception {
        checkDocker();
    }

    private static void checkDocker() throws Exception {
        Map<String, List<String>> info = DockerUtil.getVersion();
        if (info.containsKey(ShellUtil.OUTPUT_STREAM_KEY)) {
            List<String> out = info.get(ShellUtil.OUTPUT_STREAM_KEY);
            String[] client = out.get(1).split("[ \t]");
            String[] server = out.get(9).split("[ \t]");
            System.out.println("docker version:");
            System.out.println("\tClient " + client);
            System.out.println("\tServer " + server);
        } else {
            throw new Exception("failed to get docker version" + JSON.toJSONString(info.get(ShellUtil.ERROR_STREAM_KEY)));
        }
    }
}
