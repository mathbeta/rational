package com.mathbeta.rational.master.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mathbeta.rational.common.entity.Host;
import com.mathbeta.rational.common.utils.*;
import com.mathbeta.rational.master.service.impl.HostService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 17-4-22.
 */
public class CommandUtil {
    private static Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    private static CuratorFramework framework = ZkUtil.getFramework(ConfigHelper.getValue("zookeeper.url"), Constants.ZK_NS);
    private static String[] parentNodes = {"/master", "/minion/minions", "/minion/commands/request", "/minion/commands/response"};
    private static Map<String, String> responseMap = Maps.newHashMap();

    // ensure the framework can be closed properly
    public static void close() {
        if (framework != null) {
            framework.close();
        }
    }

    public static String operateDocker(String op, String ip) throws Exception {
        Map<Object, Object> message = MapUtil.buildHashMap("op", op, "cmd", "echo good");
        byte[] data = JSON.toJSONString(message).getBytes();
        String id = IdGenerator.generateId();
        String requestPath = "/minion/commands/request/" + ip + "/" + id;
        String responsePath = "/minion/commands/response/" + ip + "/" + id;
        ZkUtil.createNode(framework, requestPath, data, CreateMode.PERSISTENT);
//        ZkUtil.createNode(framework, responsePath, ZkUtil.seedValue, CreateMode.PERSISTENT);

        // a count down latch for waiting the response
        final CountDownLatch latch = new CountDownLatch(1);

        NodeCache cache = ZkUtil.watchNode(framework, responsePath, new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                byte[] bytes = ZkUtil.getNode(framework, responsePath);
                responseMap.put(id, new String(bytes));

                latch.countDown();
            }
        });

        latch.await();
        cache.close();
        ZkUtil.deleteNode(framework, requestPath);
        ZkUtil.deleteNode(framework, responsePath);

        return responseMap.remove(id);
    }

    public static void listenMinions() {
        try {
            PathChildrenCache cache = ZkUtil.watchChildren(framework, "/minion/minions", new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                    String path = pathChildrenCacheEvent.getData().getPath();
                    String ip = path.substring(path.lastIndexOf("/") + 1);
                    byte[] b = ZkUtil.getNode(framework, "/minion/minions/" + ip);
                    String conf = new String(b);

                    PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
                    if (type == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                        logger.info("minion registered to masters, ip is {}, minion info: {}", ip, conf);
                    } else if (type == PathChildrenCacheEvent.Type.CHILD_REMOVED) {
                        logger.info("minion was removed from masters, ip is {}, minion info: {}", ip, conf);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to listen minion changes", e);
        }
    }

    public static void createZkNodes() {
        try {
            for (String node : parentNodes) {
                if (!ZkUtil.existNode(framework, node)) {
                    ZkUtil.createNode(framework, node, null, CreateMode.PERSISTENT);
                }
            }

            // create nodes for all minions
            HostService hostService = (HostService) ServiceBeanContext.getInstance().getBean("hostService");
            List<Host> hosts = hostService.queryByParams(null);
            if (hosts != null && !hosts.isEmpty()) {
                for (Host host : hosts) {
                    String ip = host.getIp();
                    String requestPath = "/minion/commands/request/" + ip;
                    String responsePath = "/minion/commands/response/" + ip;
                    if (!ZkUtil.existNode(framework, requestPath)) {
                        ZkUtil.createNode(framework, requestPath, null, CreateMode.PERSISTENT);
                    }
                    if (!ZkUtil.existNode(framework, responsePath)) {
                        ZkUtil.createNode(framework, responsePath, null, CreateMode.PERSISTENT);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
