package com.fc.springsecurity.service.impl;

import com.fc.springsecurity.common.Constants;
import com.fc.springsecurity.common.ResponseResult;
import com.fc.springsecurity.entity.LoginUser;
import com.fc.springsecurity.entity.SysUser;
import com.fc.springsecurity.expression.KaptchaNotMatchException;
import com.fc.springsecurity.service.LoginService;
import com.fc.springsecurity.utils.JwtUtil;
import com.fc.springsecurity.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(SysUser sysUser) {
        //1.使用AuthenticationManager的authenticate方法进行用户认证
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(),sysUser.getPassword());

        //对authentication和UserDetails进行匹配
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //2.如果认证没有通过,给出错误提示
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登录失败");
        }

        //3.如果认证通过,使用userId生成一个JWT,并将其保存到ResponseResult中 返回

        //3.1 获取经过身份验证的用户主体信息
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();

        //3.2 获取UserId 生成JWT
        String userId = loginUser.getSysUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //4. 将用户信息存储到Redis中,在下一次请求时能够识别出用户,userId作为key
        redisCache.setCacheObject("login:"+userId, loginUser);

        //5. 封装ResponseResult,并返回
        HashMap<String, String> map = new HashMap<>();
        map.put("token",jwt);

        return new ResponseResult(200,"登录成功",map);
    }

    /**
     * 带验证码登录
     * @param userName
     * @param password
     * @param code
     * @param uuid
     * @return
     */
    @Override
    public String login(String userName, String password, String code, String uuid) {

        //1.redis 中获取验证码
        String redisKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(redisKey);
        redisCache.deleteObject(captcha);

        if (redisKey == null || !code.equalsIgnoreCase(captcha)) {
            throw new KaptchaNotMatchException("验证码错误");
        }

        //对authentication和UserDetails进行匹配
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

        //2.如果认证没有通过,给出错误提示
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登录失败");
        }

        //3.如果认证通过,使用userId生成一个JWT,并将其保存到ResponseResult中 返回

        //3.1 获取经过身份验证的用户主体信息
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();

        //3.2 获取UserId 生成JWT
        String userId = loginUser.getSysUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //4. 将用户信息存储到Redis中,在下一次请求时能够识别出用户,userId作为key
        redisCache.setCacheObject("login:"+userId, loginUser);


        return jwt;
    }
}
