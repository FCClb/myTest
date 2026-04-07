package com.fc.springsecurity_example2.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CheckCode implements Serializable {

    //验证字符
    private String code;

    //过期时间
    private LocalDateTime expireTime;

    public CheckCode(String code, int expireTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);
    }

    public CheckCode(String code) {

        //默认 验证码60秒过期
        this(code, 60);
    }

    //是否过期
    public Boolean isExpired() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }

    public String getCode() {
        return code;
    }

}
