package com.mathbeta.rational.common.zkmq;

import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 17-4-23.
 */
public class Server extends Client {
    private static Logger logger = LoggerFactory.getLogger(Server.class);
    private Map<String, PathChildrenCache> cacheMap = Maps.newHashMap();

    public Server() {
    }

    public Server(String zookeeperUrl, String zkNamespace) {
        super(zookeeperUrl, zkNamespace);
    }

    public void listen(String queue, IListener listener) {
        try {
            String queueRoot = "/" + queue;
            // create queue root if needed
            if (client.checkExists().forPath(queueRoot) == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(queueRoot, seedValue);
            }

            PathChildrenCache cache = new PathChildrenCache(client, queueRoot+"/req", false);
            cache.start();
            cache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                    if (pathChildrenCacheEvent.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
                        String path = pathChildrenCacheEvent.getData().getPath();
                        String id = getId(path);
                        String reqPath = queueRoot+"/req/" + id;

//                        PathChildrenCache reqCache = new PathChildrenCache(client, reqPath, false);
//                        reqCache.start();
//                        CountDownLatch latch = new CountDownLatch(1);
//                        reqCache.getListenable().addListener(new PathChildrenCacheListener() {
//                            @Override
//                            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
//                                if (pathChildrenCacheEvent.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED) {
//                                    latch.countDown();
//                                }
//                            }
//                        });
//                        latch.await();
//                        reqCache.close();
                        byte[] message = client.getData().forPath(reqPath);
                        byte[] response = listener.handle(id, message);
                        client.setData().forPath(queueRoot + "/res/" + id, response);
                    }
                }

                private String getId(String path) {
//                    if (path.startsWith(queueRoot)) {
//                        int i = queueRoot.length() + 1;
//                        int j = path.indexOf("/", i);
//                        if (j >= 0) {
//                            return path.substring(i, j);
//                        }
//                        return path.substring(i);
//                    }
//                    return "";
                    return path.substring(path.lastIndexOf("/")+1);
                }
            });

            cacheMap.put(queue, cache);
        } catch (Exception e) {
            logger.error("failed to listen to the queue {}, the error occurred: {}", queue, e);
            e.printStackTrace();
        }
    }

    public void removeListener(String queue) {
        try {
            PathChildrenCache cache = cacheMap.remove(queue);
            if (cache != null) {
                cache.close();
            }
        } catch (IOException e) {
            logger.error("failed to remove listener for queue {}, the error occurred: {}", queue, e);
        }
    }
}
