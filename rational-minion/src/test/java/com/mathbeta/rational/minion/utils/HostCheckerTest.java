package com.mathbeta.rational.minion.utils;

import org.junit.Test;

/**
 * Created by 147458 on 2017/4/25.
 */
public class HostCheckerTest {
    @Test
    public void getOsInfo() {
        System.out.println(HostChecker.getConf());
    }

    @Test
    public void getMacAndIp() {
        try {
            System.out.println(HostChecker.getMacAddress());
            System.out.println(HostChecker.getIpAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
