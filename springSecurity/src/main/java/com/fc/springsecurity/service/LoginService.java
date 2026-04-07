package com.fc.springsecurity.service;

import com.fc.springsecurity.common.ResponseResult;
import com.fc.springsecurity.entity.SysUser;

public interface LoginService {

    ResponseResult login(SysUser sysUser);

    String login(String userName, String password, String code, String uuid);
}

