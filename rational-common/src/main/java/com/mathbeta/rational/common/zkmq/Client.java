package com.mathbeta.rational.common.zkmq;

import com.mathbeta.rational.common.utils.IdGenerator;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 17-4-23.
 */
public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    protected CuratorFramework client;
    protected byte[] seedValue = new byte[0];
    private String zookeeperUrl;
    private String zkNamespace;

    public Client() {
    }

    public Client(String zookeeperUrl, String zkNamespace) {
        this.setZookeeperUrl(zookeeperUrl);
        this.setZkNamespace(zkNamespace);
    }

    public void start() {
        if (client == null) {
            client = CuratorFrameworkFactory.builder().connectString(zookeeperUrl)
                    .sessionTimeoutMs(30000).connectionTimeoutMs(3000).canBeReadOnly(false)
                    .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                    .namespace(zkNamespace).defaultData(null).build();
            client.start();

            // create ns
//            try {
//                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(zkNamespace, seedValue);
//            } catch (Exception e) {
//                logger.error("failed to create namespace [{}] for the mq client, exception occurred {}", zkNamespace, e);
//            }
        }
    }

    public void close() {
        if (client != null) {
            client.close();
        }
    }

    public byte[] sendAndReceive(String queue, byte[] message) {
        byte[] response = null;
        String id = IdGenerator.generateId();
        try {
            // TODO split data into peaces with length less than 1MB and store them into sequential nodes
            String path = "/" + queue;
            String reqPath = path + "/req/" + id;
            String resPath = path + "/res/" + id;
            if (client.checkExists().forPath(path) == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, seedValue);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(reqPath, message);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(resPath, seedValue);

            NodeCache cache = new NodeCache(client, resPath, false);
            cache.start();
            CountDownLatch latch = new CountDownLatch(1);
            cache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    latch.countDown();
                }
            });
            latch.await();
            cache.close();
            response = client.getData().forPath(resPath);
            if (client.checkExists().forPath(reqPath) != null) {
                client.delete().inBackground().forPath(reqPath);
            }
            if (client.checkExists().forPath(resPath) != null) {
                client.delete().inBackground().forPath(resPath);
            }
//            if (client.checkExists().forPath(path) != null) {
//                client.delete().inBackground().forPath(path);
//            }
            logger.debug("successfully send and receive message to/from queue {}", queue);
        } catch (Exception e) {
            logger.error("failed to send and receive message to/from queue {}, the error is {}", queue, e);
            e.printStackTrace();
        }
        return response;
    }

    public String getZookeeperUrl() {
        return zookeeperUrl;
    }

    public void setZookeeperUrl(String zookeeperUrl) {
        this.zookeeperUrl = zookeeperUrl;
    }

    public String getZkNamespace() {
        return zkNamespace;
    }

    public void setZkNamespace(String zkNamespace) {
        this.zkNamespace = zkNamespace;
    }
}
