package cn.itsource.aigou.client.impl;

import cn.itsource.aigou.client.RedisClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * common服务器feign拖地数据
 */
public class RedisClientImpl implements RedisClient{
    public String get(String key) {
        return "{\"message\":\"服务器异常\"}";
    }

    public void set(String key, String value) {

    }
}
