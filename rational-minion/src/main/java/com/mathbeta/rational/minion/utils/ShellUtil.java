package com.mathbeta.rational.minion.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mathbeta.rational.common.utils.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 17-4-16.
 */
public class ShellUtil {
    private static Logger logger = LoggerFactory.getLogger(ShellUtil.class);
    private static ExecutorService pool = Executors.newFixedThreadPool(Integer.parseInt(ConfigHelper.getValue("exec.pool.size")));
    public static final String OUTPUT_STREAM_KEY = "output";
    public static final String ERROR_STREAM_KEY = "error";

    public static Map<String, List<String>> exec(String cmd) {
        List<String> output = Lists.newArrayList();
        List<String> error = Lists.newArrayList();
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream errorStream = process.getErrorStream();
            InputStream inputStream = process.getInputStream();
            CountDownLatch latch = new CountDownLatch(2);
            pool.submit(new StreamReader(inputStream, latch, output));
            pool.submit(new StreamReader(errorStream, latch, error));

            latch.await();
            errorStream.close();
            inputStream.close();
        } catch (Exception e) {
            logger.error("exec cmd failed: " + cmd, e);
        }
        Map<String, List<String>> result = Maps.newHashMap();
        result.put(OUTPUT_STREAM_KEY, output);
        result.put(ERROR_STREAM_KEY, error);
        return result;
    }

    private static class StreamReader extends Thread {
        private InputStream is;
        private CountDownLatch latch;
        private List<String> result;

        public StreamReader(InputStream is, CountDownLatch latch, List<String> result) {
            this.is = is;
            this.latch = latch;
            this.result = result;
        }

        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            try {
                while ((str = br.readLine()) != null) {
                    result.add(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (latch != null) {
                    latch.countDown();
                }
            }
        }
    }
}
