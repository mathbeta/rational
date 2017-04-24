package com.mathbeta.rational.minion.main;

import com.google.common.collect.Maps;
import com.mathbeta.rational.common.utils.DateUtil;
import com.mathbeta.rational.minion.utils.CommandUtil;
import com.mathbeta.rational.minion.utils.HostChecker;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 17-4-16.
 */
public class StartMinion {
    public static void main(String[] args) {
        try {
//            HostChecker.check();
        } catch (Exception e) {
            System.err.println("Failed to check host configurations, " + e.getMessage());
            System.exit(-1);
        }
        System.out.println("====================================================================================================");
        System.out.println("[" + DateUtil.getDateTime() + "] minion is starting......");
        start();
        System.out.println("[" + DateUtil.getDateTime() + "] minion has been started.");
        System.out.println("====================================================================================================");
    }

    private static void start() {
        // register the minion info for master auto discovery
        try {
            String ip = HostChecker.getIp();
            // mem, cpu...
            Map<String,Object> info = Maps.newHashMap();
            info.put("cpus", 2);
            info.put("mem",2048);
            CommandUtil.register(ip,info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // listen for the commands from master
            CommandUtil.watchCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
