package cn.itsource.aigou.client;

import cn.itsource.aigou.client.impl.RedisClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "AIGOU-COMMON",fallback = RedisClientImpl.class)
public interface RedisClient {
    /*获取redis的缓存数据*/
    @GetMapping("/redis")
    public String get(@RequestParam("key") String key);
        /**
         * 设置redis的缓存数据
         * @param key
         * @param value
         */
    @PostMapping("/redis")
    public void set(@RequestParam("key") String key,@RequestParam("value") String value);
}
