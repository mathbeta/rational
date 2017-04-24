package com.mathbeta.rational.master.utils;

import org.junit.Test;

/**
 * Created by Administrator on 17-4-16.
 */
public class LeaderElectionTest {
    @Test
    public void test() {
        while (true) {
            System.out.println("I am the leader? " + LeaderElection.getInstance().isLeader());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
