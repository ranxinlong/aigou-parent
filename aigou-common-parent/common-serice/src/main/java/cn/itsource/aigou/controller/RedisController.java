package cn.itsource.aigou.controller;


import cn.itsource.aigou.util.RedisUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedisController {

    /*获取redis的缓存数据*/
    @GetMapping("/redis")
    public String get(@RequestParam("key") String key){
        return RedisUtils.INSTANCE.get(key);
    }

    /**
     * 设置redis的缓存数据
     * @param key
     * @param value
     */
    @PostMapping("/redis")
    public void set(@RequestParam("key") String key,@RequestParam("value") String value){
        RedisUtils.INSTANCE.set(key, value);
    }
}
