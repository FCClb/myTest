package com.fc.springsecurity.controller;

import com.fc.springsecurity.common.Constants;
import com.fc.springsecurity.common.ResponseResult;
import com.fc.springsecurity.utils.RedisCache;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 生成验证码
 */
@RestController
public class CaptchaController {

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取验证码
     */
    @GetMapping("/captchaImage")
    public ResponseResult getCode(HttpServletResponse response) {

        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        //生成验证码 以及验证码唯一标识
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //构建redis中的key
        String key = Constants.CAPTCHA_CODE_KEY + uuid;
        String code = specCaptcha.text().toLowerCase();

        //保存到redis
        redisCache.setCacheObject(key, code, 1000, TimeUnit.SECONDS);

        //创建map
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("img", specCaptcha.toBase64());

        return new ResponseResult(200, "验证码获取成功", map);

    }
}
