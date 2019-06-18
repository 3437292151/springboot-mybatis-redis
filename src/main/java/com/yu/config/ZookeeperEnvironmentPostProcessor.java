package com.yu.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: yuchanglong
 * @Date: 2019-6-17
 * @Description:
 */
public class ZookeeperEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication) {
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        PropertySource<?> curator = propertySources.get("applicationConfig: [classpath:/application.yml]");
        Integer retryCount = Integer.class.cast(curator.getProperty("curator.retryCount"));
        Integer elapsedTimeMs = Integer.class.cast(curator.getProperty("curator.elapsedTimeMs"));
        String connectString = String.class.cast(curator.getProperty("curator.connectString"));
        Integer sessionTimeoutMs = Integer.class.cast(curator.getProperty("curator.sessionTimeoutMs"));
        Integer connectionTimeoutMs = Integer.class.cast(curator.getProperty("curator.connectionTimeoutMs"));
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
                connectString,
                sessionTimeoutMs,
                connectionTimeoutMs,
                new RetryNTimes(retryCount, elapsedTimeMs));
        curatorFramework.start();
        Map<String, Object> map = new HashMap<>();
        try {curatorFramework.getChildren().forPath("/congfigtest");
            curatorFramework.getChildren().forPath("/congfigtest").forEach(e -> {
                try {
                    String value = new String(curatorFramework.getData().forPath("/congfigtest/"+e));
                    map.put(e, value);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            } );
        } catch (Exception e) {
            e.printStackTrace();
        }
        MapPropertySource mapPropertySource = new MapPropertySource("zkMapPropertySource", map);
        propertySources.addFirst(mapPropertySource);
    }
}
