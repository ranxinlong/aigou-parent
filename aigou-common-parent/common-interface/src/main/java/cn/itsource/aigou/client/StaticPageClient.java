package cn.itsource.aigou.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 前端生成静态页面的fejgn，因为没有返回数据所以不需要托底数据
 */
@Component
@FeignClient(value = "AIGOU-COMMON")
public interface StaticPageClient {
    @PostMapping("/page")
    public void generateStaticPage(@RequestParam("templatePath") String templatePath,
                                   @RequestParam("targetPath") String targetPath,
                                   @RequestBody Object model);
}
