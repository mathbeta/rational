package com.mathbeta.rational.common.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

/**
 * Created by Administrator on 17-4-16.
 */
public class ZkUtilTest {
    @Test
    public void test() {
        CuratorFramework framework = ZkUtil.getFramework("127.0.0.1:2181", "test");
        try {
            String path = ZkUtil.createNode(framework, "/node", null, CreateMode.PERSISTENT_SEQUENTIAL);

            Object node1 = ZkUtil.getNode(framework, path);
            System.out.println(node1);

            PathChildrenCache cache = ZkUtil.watchChildren(framework, path, new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                    if (pathChildrenCacheEvent.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                        System.out.println("child added for node1");
                    }
                }
            });

            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000L);
                        ZkUtil.createNode(framework, path + "/child", null, CreateMode.EPHEMERAL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

//            ZkUtil.deleteNode(framework, "node1");

            Thread.sleep(10000L);
            cache.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            framework.close();
        }
    }
}
