package cn.itsource.aigou.controller;

import cn.itsource.aigou.domain.User;
import cn.itsource.basic.util.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户登录的controller")
public class LoginController {

    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public AjaxResult login(@RequestBody User user){
        if ("admin".equals(user.getUsername()) && "admin".equals(user.getPassword())){
            return AjaxResult.me().setSuccess(true).setMessage("登录成功").setRestObj(user);
        }
        return AjaxResult.me().setSuccess(false).setMessage("登录失败");
    }
}
