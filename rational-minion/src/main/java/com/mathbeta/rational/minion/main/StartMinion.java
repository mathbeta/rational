package com.mathbeta.rational.minion.main;

import com.mathbeta.rational.common.utils.DateUtil;
import com.mathbeta.rational.minion.utils.HostChecker;

/**
 * Created by Administrator on 17-4-16.
 */
public class StartMinion {
    public static void main(String[] args) {
        try {
            HostChecker.check();
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
    }
}
