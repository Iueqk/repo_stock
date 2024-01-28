package com.cqut.stock.controller;

import com.cqut.stock.common.ApiRestResponse;
import com.cqut.stock.pojo.entity.SysUser;
import com.cqut.stock.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "用户接口模块")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("根据用户名查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true,
                    dataType = "string",paramType = "path")
    })
    @GetMapping("/user/{username}")
    public ApiRestResponse selectByUsername(@PathVariable("username") String username) {
        return ApiRestResponse.success(userService.selectUserByUsername(username));
    }

    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    public ApiRestResponse getCaptchaCode() {
        Map captchaCode = userService.getCaptchaCode();
        return ApiRestResponse.success(captchaCode);
    }
}
