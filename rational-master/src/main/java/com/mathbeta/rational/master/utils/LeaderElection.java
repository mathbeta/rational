package com.mathbeta.rational.master.utils;

import com.mathbeta.rational.common.utils.ConfigHelper;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 17-4-16.
 */
public class LeaderElection {
    private static Logger logger = LoggerFactory.getLogger(LeaderElection.class);

    private static final String leaderRoot = "/com.mathbeta.rational";
    private static final String name = "master";
    private static String realName = null;

    private static boolean isLeader = false;
    private static ZooKeeper zk = null;
    private static LeaderElection instance;

    private LeaderElection() {
    }

    private void check() {
        try {
            if (zk == null) {
                zk = new ZooKeeper(ConfigHelper.getValue("zookeeper.url"), 3000, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                    }
                });
            }

            /**
             * 如果节点已存在，则直接进行leader判断
             */
            if (realName != null) {
                Stat stat = zk.exists(realName, false);
                if (stat != null) {
                    watch(zk, leaderRoot, realName, name);
                    return;
                }
            }
            Stat rootStat = zk.exists(leaderRoot, false);
            if (rootStat == null) {
                try {
                    zk.create(leaderRoot, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    logger.debug("creating leader root path successfully");
                } catch (Exception e) {
                    logger.error("failed to create leader root path", e);
                }
            } else {
                logger.debug("leader root path existed...");
            }
            rootStat = zk.exists(leaderRoot, false);
            if (rootStat == null) {
                logger.error("can not find leader root path, exit");
                return;
            }

            String path = leaderRoot + "/" + name;
            try {
                realName = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            } catch (Exception e) {
                System.err.println("leader election, creating leader path failed, exit");
                return;
            }
            logger.debug("leader election, I am " + realName);

            watch(zk, leaderRoot, realName, name);
        } catch (Exception e) {
            logger.error("leader election, error occurred...", e);
        }
    }


    // 监听链中的前一个存在的节点，如果不存在，则自己为leader
    private void watch(ZooKeeper zk, String root, String realName, String name) throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren(root, false);

        int k = -1;
        int len = name.length();
        int me = Integer.parseInt(realName.substring(root.length() + 1 + name.length()));

        String watchPath = null;
        // 找到比自己序号小的节点中，序号最大的那个，并监听它
        for (String child : children) {
            int seq = Integer.parseInt(child.substring(len));
            if (seq > k && seq < me) {
                k = seq;
                watchPath = child;
            }
        }
        if (watchPath == null) {
            isLeader = true;
            logger.debug("leader election, " + realName + " is elected as the leader");
        } else {
            isLeader = false;

            watchPath = root + "/" + watchPath;
            logger.debug("leader election, " + realName + " is watching " + watchPath);
            zk.exists(watchPath, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    // 如果被监听节点被删除，则监听链中的前一个节点
                    if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                        try {
                            watch(zk, root, realName, name);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public static LeaderElection getInstance() {
        if (instance == null) {
            synchronized (LeaderElection.class) {
                if (instance == null) {
                    instance = new LeaderElection();
                }
            }
        }
        return instance;
    }

    public boolean isLeader() {
        check();
        return isLeader;
    }
}
