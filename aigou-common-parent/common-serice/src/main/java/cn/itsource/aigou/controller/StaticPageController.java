package cn.itsource.aigou.controller;

import cn.itsource.aigou.util.VelocityUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 生成静态页面的暴露结构
 * @author rxl
 */
@RestController
public class StaticPageController {

    @PostMapping("/page")
    public void generateStaticPage(@RequestParam("templatePath") String templatePath,
                                   @RequestParam("targetPath") String targetPath,
                                   @RequestBody Object model){
        VelocityUtils.staticByTemplate(model, templatePath, targetPath);
    }
}
