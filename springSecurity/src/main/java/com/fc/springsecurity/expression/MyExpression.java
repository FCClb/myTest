package com.fc.springsecurity.expression;

import com.fc.springsecurity.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限校验
 */
@Component("my_ex")
public class MyExpression {

    /**
     * 自定义 hasAuthority
     * @param authority
     * @return
     */
    public final boolean hasAuthority(String authority) {

        //获取当前用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();

        //判断集合中是否有authority
        return permissions.contains(authority);
    }
}
