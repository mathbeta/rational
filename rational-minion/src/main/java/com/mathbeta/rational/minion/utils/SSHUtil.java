package com.mathbeta.rational.minion.utils;

import com.google.common.collect.Lists;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 17-4-16.
 */
public class SSHUtil {
    private static Logger logger = LoggerFactory.getLogger(SSHUtil.class);

    public static Session getSession(String host, int port, String userName, String passwd) {
        Session session = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(userName, host, port);
            session.setPassword(passwd);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect();
            session.isConnected();
            logger.info("主机ip：" + host + ", ssh连接成功！");
        } catch (Exception e) {
            logger.error("主机ip：" + host + ", ssh连接失败！", e);
            session = null;
        }
        return session;
    }

    public static List<String> exec(Session session, String command) {
        List<String> list = Lists.newArrayList();
        try {
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
            InputStream in = channel.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                } else {
                    list.add(line);
                }
            }
        } catch (Exception e) {
            logger.error("执行shell command失败: " + command, e.getMessage());
        }
        return list;

    }
}
