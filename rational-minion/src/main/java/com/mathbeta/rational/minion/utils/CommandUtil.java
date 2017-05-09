package com.mathbeta.rational.minion.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mathbeta.rational.common.entity.Message;
import com.mathbeta.rational.common.utils.ConfigHelper;
import com.mathbeta.rational.common.utils.Constants;
import com.mathbeta.rational.common.utils.ZkUtil;
import com.mathbeta.rational.minion.handler.DockerProcessHandlerFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 17-4-22.
 */
public class CommandUtil {
    private static Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    private static CuratorFramework framework = null;
    private static PathChildrenCache commandsCache = null;

    static {
        init();
    }

    private static void init() {
        if (framework == null) {
            synchronized (CommandUtil.class) {
                if (framework == null) {
                    framework = ZkUtil.getFramework(ConfigHelper.getValue("zookeeper.url"), Constants.ZK_NS);
                    // TODO also can get the zookeeper url from env var
                }
            }
        }
    }

    // ensure the framework can be closed properly
    public static void close() {
        if (framework != null) {
            framework.close();
        }
    }

    public static void watchCommands() throws Exception {
        String parent = "/minion/commands/request/" + HostChecker.getIp();
        commandsCache = ZkUtil.watchChildren(framework, parent, new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                if (pathChildrenCacheEvent.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                    ChildData data = pathChildrenCacheEvent.getData();
                    String path = data.getPath();
                    String id = path.substring(path.lastIndexOf("/") + 1);
                    byte[] b = ZkUtil.getNode(framework, parent + "/" + id);
//                    byte[] b = data.getData();
//                    if(b==null){
//                        b=new byte[0];
//                    }
                    String received = new String(b);
                    logger.info("received message [{}]: {}", path, received);

                    Map<String, Object> map = JSON.parseObject(received, new TypeReference<Map<String, Object>>() {
                    });
                    Constants.DockerProcessOp op = Constants.DockerProcessOp.valueOf(map.get("op").toString().toUpperCase());

                    Message message = DockerProcessHandlerFactory.getFactory().getHandler(op).handle();

                    ZkUtil.createNode(framework, "/minion/commands/response/" + HostChecker.getIp() + "/" + id, message.toString().getBytes(), CreateMode.PERSISTENT);
                }
            }
        });
    }

    public static void closeCommandsWatch() {
        if (commandsCache != null) {
            try {
                commandsCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void register(String ip, Map<String, Object> info) {
        try {
            String node = "/minion/minions/" + ip;
            String conf = JSON.toJSONString(info);
            if (ZkUtil.existNode(framework, node)) {
                ZkUtil.deleteNode(framework, node);
            }
            String path = ZkUtil.createNode(framework, node, conf.getBytes(), CreateMode.EPHEMERAL);
            logger.info("register minion to masters, path is {}, ip is {}, minion info: {}", path, ip, conf);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to register minion to master [{}]: {}", ip, e);
        }
    }
}
