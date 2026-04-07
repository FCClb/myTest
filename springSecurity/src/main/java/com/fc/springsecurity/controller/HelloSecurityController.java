package com.fc.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSecurityController {

    /**
     * @PreAuthorize()在方法执行前进行权限校验
     *  hasAnyAuthority()检查用户是否具有指定的权限
     *  @PostAuthorize()在方法执行之后进行权限校验
     */
    @RequestMapping("/hello")
    @PreAuthorize("hasAnyAuthority('system:user:list')")    //检查调用者是否具有指定权限
    public String hello() {
        return "hello Spring Security!!!";
    }

    @RequestMapping("/ok")
    @PreAuthorize("hasAnyAuthority('admin','system:role:list','system:menu:list')") //检查调用者是否具有指定的任何一个权限
    public String ok() {

        return "ok Spring Security!!!";
    }

    @RequestMapping("/level1")
    @PreAuthorize("hasRole('common') OR hasAnyAuthority('system:user:list','system:role:list')")
    public String level1() {

        return "level1 Spring Security!!!";
    }

    @RequestMapping("/level2")
    @PreAuthorize("hasAnyRole('admin','test') OR hasAuthority('system:menu:list')")
    public String level2() {

        return "level2 Spring Security!!!";
    }

    @RequestMapping("/myYes")
//    @PreAuthorize("@my_ex.hasAuthority('system:menu:list')")
    public String myYes() {

        return "myYes Spring Security!!!";
    }
}
