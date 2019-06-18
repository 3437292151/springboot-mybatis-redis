package com.yu.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: yuchanglong
 * @Date: 2019-6-18
 * @Description: zookeeper 环境监听
 */
@Component
@Slf4j
public class ZookeeperEnvironmentWatcher {

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private PayConfig payConfig;

    @PostConstruct
    private void watcher() throws Exception {
        log.info("zookeeper watcher!!");
        String keyPath = "/congfigtest";
        final PathChildrenCache cache = new PathChildrenCache(curatorFramework, keyPath, false);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener((client, event) -> {
            log.info("event type:{}", event.getType());
            if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                String basePath = event.getData().getPath();
                String path = basePath.substring(basePath.indexOf(".", 1) + 1, basePath.length());
                if (path.contains("alipay")){
                    payConfig.getAlipay().put(path.replace("alipay.", ""), new String(client.getData().forPath(event.getData().getPath())));
                }else {
                    payConfig.getWeixin().put(path.replace("weixin.", ""), new String(client.getData().forPath(event.getData().getPath())));
                }
            }
        });
    }
}
