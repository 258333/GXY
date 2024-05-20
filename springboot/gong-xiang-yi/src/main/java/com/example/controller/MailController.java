package com.example.controller;

import com.example.pojo.Result;
import com.example.service.IMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "邮箱业务管理")
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private IMailService mailService;

    /**
     * 获取重置密码的验证码
     */
    @ApiOperation(value = "获取重置密码的验证码", notes = "获取重置密码的验证码", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "（账号）", required = true, paramType = "form"),
            @ApiImplicitParam(name = "email", value = "邮箱地址", required = true, paramType = "form"),
    })
    @GetMapping("/getCode")
    public Result getCode(String username, String email){
        return mailService.getCode(username,email);
    }
}
