package com.fc.springsecurity.controller;

import com.fc.springsecurity.common.ResponseResult;
import com.fc.springsecurity.entity.LoginBody;
import com.fc.springsecurity.entity.LoginUser;
import com.fc.springsecurity.entity.SysUser;
import com.fc.springsecurity.service.LoginService;
import com.fc.springsecurity.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录
     * @param sysUser
     * @return
     */
//    @CrossOrigin(origins = "http://localhost:8080")   局部配置允许跨域请求
//    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody SysUser sysUser) {

        return loginService.login(sysUser);
    }

    /**
     * 登录 + 验证码
     * @param loginBody
     * @return
     */
//    @CrossOrigin(origins = "http://localhost:8080")   局部配置允许跨域请求
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody LoginBody loginBody) {

        //生成令牌
        String token = loginService.login(loginBody.getUserName(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());

        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);

        return new ResponseResult(200, "登录成功", map);
    }

    @GetMapping("/user/logout")
    /**
     * 登出
     */
    public ResponseResult logout() {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authenticationToken)) {
            throw new RuntimeException("获取用户认证信息失败");
        }

        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        Long userId = loginUser.getSysUser().getUserId();

        //删除redis中的用户信息
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult(200, "注销成功");
    }
}
