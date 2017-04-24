package com.mathbeta.rational.common.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * Created by Administrator on 17-4-16.
 */
public class ZkUtil {
    public static final byte[] seedValue = new byte[0];

    public static CuratorFramework getFramework(String url, String namespace) {
        CuratorFramework framework = CuratorFrameworkFactory.builder().connectString(url)
                .sessionTimeoutMs(30000).connectionTimeoutMs(3000).canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace(namespace).defaultData(null).build();
        framework.start();
        return framework;
    }

    public List<String> getChildren(CuratorFramework framework, String path) throws  Exception {
        return framework.getChildren().forPath(path);
    }

    public static byte[] getNode(CuratorFramework framework, String path) throws Exception {
        byte[] b = framework.getData().forPath(path);
        if (b == null) {
            b = new byte[0];
        }
        return b;
    }

    public static String createNode(CuratorFramework framework, String path, byte[] data, CreateMode mode) throws Exception {
        return framework.create().creatingParentsIfNeeded().withMode(mode).forPath(path, data);
    }

    public static boolean existNode(CuratorFramework framework, String path) throws Exception {
        return framework.checkExists().forPath(path) != null;
    }

    public static void updateNode(CuratorFramework framework, String path, byte[] data) throws Exception {
        if (framework.checkExists().forPath(path) != null) {
            framework.setData().forPath(path, data);
        }
    }

    public static void deleteNode(CuratorFramework framework, String path) throws Exception {
        if (framework.checkExists().forPath(path) != null) {
            framework.delete().inBackground().forPath(path);
//            if ( cacheMap.containsKey( path ) ){
//                cacheMap.remove( path ).close();
//            }
        }
    }

    public static NodeCache watchNode(CuratorFramework framework, String path, NodeCacheListener listener) throws Exception {
        NodeCache cache = new NodeCache(framework, path, false);
        cache.start();
        cache.getListenable().addListener(listener);
        return cache;
    }

    public static PathChildrenCache watchChildren(CuratorFramework framework, String path, PathChildrenCacheListener listener) throws Exception {
//        if ( cacheMap.containsKey( path ) ){
//            cacheMap.remove(path).close();
//        }
        PathChildrenCache cache = new PathChildrenCache(framework, path, false);
        cache.start();
        cache.getListenable().addListener(listener);
        return cache;
//        cacheMap.put( path, cache );
    }
}
