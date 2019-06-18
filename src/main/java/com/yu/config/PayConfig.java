package com.yu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: yuchanglong
 * @Date: 2019-6-18
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix="pay")
public class PayConfig {

    private Map<String, String> alipay = new HashMap<>();

    private Map<String, String> weixin = new HashMap<>();

    public Map<String, String> getAlipay() {
        return alipay;
    }

    public void setAlipay(Map<String, String> alipay) {
        this.alipay = alipay;
    }

    public Map<String, String> getWeixin() {
        return weixin;
    }

    public void setWeixin(Map<String, String> weixin) {
        this.weixin = weixin;
    }
}
