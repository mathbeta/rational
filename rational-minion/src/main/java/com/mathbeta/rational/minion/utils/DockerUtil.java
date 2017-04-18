package com.mathbeta.rational.minion.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-4-16.
 */
public class DockerUtil {
    private static Logger logger = LoggerFactory.getLogger(DockerUtil.class);
    public static final String ENV_FILE_UBUNTU = "/etc/docker/default";
    public static final String ENV_FILE_CENTOS = "/etc/sysconfig/docker";
    public static final String DOCKER_OPT_ENV_NAME = "options";

    /**
     * 查询容器日志
     *
     * @param containerId
     * @return
     */
    public static String getLogs(String containerId) {
        Map<String, List<String>> result = ShellUtil.exec("docker logs " + containerId);
        return String.join("\n", result.get(ShellUtil.OUTPUT_STREAM_KEY));
    }

    /**
     * 创建容器
     *
     * @param options   创建选项，如{"--name":"test","-d":"","-p":"8080:80","-v":"/tmp/test:/root/mytest"}
     * @param imageName 镜像名称
     * @return 容器id
     */
    public static String create(Map<String, String> options, String imageName) {
        String cmd = getCommand("docker create ", options, imageName);
        Map<String, List<String>> result = ShellUtil.exec(cmd);
        return String.join("\n", result.get(ShellUtil.OUTPUT_STREAM_KEY));
    }

    private static String getCommand(String cmd, Map<String, String> options, String imageName) {
        StringBuilder sb = new StringBuilder(cmd);
        if (options != null && !options.isEmpty()) {
            Iterator<String> it = options.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                sb.append(" ").append(key);
                String val = options.get(key);
                // 没有值的选项只有key
                if (val != null && !val.isEmpty()) {
                    sb.append("=").append(val);
                }
            }
        }
        sb.append(" ").append(imageName);
        return sb.toString();
    }

    /**
     * 直接运行一个容器
     *
     * @param options
     * @param imageName
     * @return
     */
    public static String run(Map<String, String> options, String imageName) {
        String cmd = getCommand("docker run ", options, imageName);
        Map<String, List<String>> result = ShellUtil.exec(cmd);
        return String.join("\n", result.get(ShellUtil.OUTPUT_STREAM_KEY));
    }

    /**
     * 在容器中运行一个命令
     *
     * @param options exec命令选项
     * @param containerId 容器id
     * @param command 需要在容器中执行的命令，包含命令需要的参数，如'tail -n 300 /data/test.log'
     * @return
     */
    public static String exec(Map<String, String> options, String containerId, String command) {
        String cmd = getCommand("docker exec ", options, containerId) + " " + command;
        Map<String, List<String>> result = ShellUtil.exec(cmd);
        return String.join("\n", result.get(ShellUtil.OUTPUT_STREAM_KEY));
    }

    /**
     * 启动容器
     *
     * @param containerId
     * @return
     */
    public static String start(String containerId) {
        Map<String, List<String>> result = ShellUtil.exec("docker start " + containerId);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }

    /**
     * 重启容器
     *
     * @param containerId
     * @return
     */
    public static String restart(String containerId) {
        Map<String, List<String>> result = ShellUtil.exec("docker restart " + containerId);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }

    /**
     * 暂停容器
     *
     * @param containerId
     * @return
     */
    public static String pause(String containerId) {
        Map<String, List<String>> result = ShellUtil.exec("docker pause " + containerId);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }

    /**
     * 取消暂停容器
     *
     * @param containerId
     * @return
     */
    public static String unpause(String containerId) {
        Map<String, List<String>> result = ShellUtil.exec("docker unpause " + containerId);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }

    /**
     * 停止容器
     *
     * @param containerId
     * @return
     */
    public static String stop(String containerId) {
        Map<String, List<String>> result = ShellUtil.exec("docker stop " + containerId);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }

    /**
     * 杀死容器
     *
     * @param containerId
     * @return
     */
    public static String kill(String containerId) {
        Map<String, List<String>> result = ShellUtil.exec("docker kill " + containerId);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }

    /**
     * 删除容器
     *
     * @param containerId
     * @return
     */
    public static String rm(String containerId) {
        Map<String, List<String>> result = ShellUtil.exec("docker rm -f " + containerId);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }

    /**
     * 删除镜像
     *
     * @param imageName
     * @return
     */
    public static String rmi(String imageName) {
        Map<String, List<String>> result = ShellUtil.exec("docker rmi -f " + imageName);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }

    /**
     * 拉取镜像
     *
     * @param imageName
     * @return
     */
    public static String pull(String imageName) {
        Map<String, List<String>> result = ShellUtil.exec("docker pull " + imageName);
        return result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
    }


    /**
     * 查询docker版本
     *
     * @return
     */
    public static Map<String, List<String>> getVersion() {
//        Map<String, List<String>> result = ShellUtil.exec("docker version");
//        String version = null;
//        if (result.get(ShellUtil.ERROR_STREAM_KEY).isEmpty()) {
//            version = result.get(ShellUtil.OUTPUT_STREAM_KEY).get(0);
//        } else {
//            logger.error("exec 'docker version' failed", result.get(ShellUtil.ERROR_STREAM_KEY));
//            version = "";
//        }
//        return version;
        return ShellUtil.exec("docker version");
    }

    /**
     * 修改docker配置文件中的daemon选项，执行时需要docker daemon进程已停止
     *
     * @param options
     * @return
     */
    public static boolean modifyDockerOptions(Map<String, String> options) {
        File file = null;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("ubuntu")) {
            file = new File(ENV_FILE_UBUNTU);
        }
        if (os.contains("centos")) {
            file = new File(ENV_FILE_CENTOS);
        }
        if (file == null) {
            logger.error("can not modify docker options, unsupported os systems: " + os);
            return false;
        }
        File tmp = new File("configs/docker");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
            String str = null;
            while ((str = br.readLine()) != null) {
                String s = str.trim();
                if (s.startsWith(DOCKER_OPT_ENV_NAME)) {
                    String[] kv = s.split("=", 2);
                    if (kv != null && kv.length == 2) {
                        String v = kv[1];
                        if (v.startsWith("\"")) {
                            v = v.substring(1);
                        }
                        if (v.endsWith("\"")) {
                            v = v.substring(0, v.length() - 1);
                        }
                        String[] opts = v.split(" ");
                        if (opts != null && opts.length > 0) {
                            for (String opt : opts) {
                                kv = opt.split("=", 2);
                                if (kv != null && !options.containsKey(kv[0])) {
                                    v = "";
                                    if (kv.length == 2) {
                                        v = kv[1];
                                    }
                                    options.put(kv[0], v);
                                }
                            }
                        }
                    }
                    bw.append(DOCKER_OPT_ENV_NAME).append("=").append("\"");
                    List<String> list = Lists.newArrayList();
                    Iterator<String> it = options.keySet().iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        String val = options.get(key);
                        list.add(key + "=" + val);
                    }
                    bw.append(String.join(" ", list)).append("\n");
                } else {
                    bw.append(str).append("\n");
                }
            }
            Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            logger.error("failed to modify docker options", e);
        }

        return false;
    }
}
