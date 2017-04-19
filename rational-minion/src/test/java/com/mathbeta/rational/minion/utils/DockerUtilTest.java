package com.mathbeta.rational.minion.utils;

import org.junit.Test;

import java.io.File;

/**
 * Created by Administrator on 17-4-16.
 */
public class DockerUtilTest {
    @Test
    public void getOsNameTest() {
        System.out.println(System.getProperty("os.name"));
    }

    @Test
    public void fileToPathTest() {
        File file = new File("configs/config.properties");
        System.out.println(file.toPath());
    }

    @Test
    public void stringTest() {
        String[] s = "Version:      17.03-ce-1".split("[ \t]");
        if (s != null) {
//            for (String str : s) {
//                System.out.println(str);
//            }
            System.out.println(s[s.length-1]);
        }
    }
}
