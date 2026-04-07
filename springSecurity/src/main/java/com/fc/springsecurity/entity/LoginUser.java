package com.fc.springsecurity.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoginUser implements UserDetails {

    private SysUser sysUser;

    //存储权限的集合
    private List<String> permissions;

    //authorities权限集合
    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> authorities;

    //存储角色信息的集合
    private List<String> roles;

    public LoginUser() {
    }

    public LoginUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public LoginUser(SysUser sysUser, List<String> list) {
        this.sysUser = sysUser;
        this.permissions = list;
    }

    public LoginUser(SysUser sysUser, List<String> permissions, List<String> roles) {
        this.sysUser = sysUser;
        this.permissions = permissions;
        this.roles = roles;
    }

    /**
     * 用于获取用户被授予的权限, 可以用于实现访问控制
     * @param
     * @return: java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //将permissions集合中的String类型的权限信息，转换为SimpleGrantedAuthority类型

        //方式一
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (String permission : permissions) {
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
//            authorities.add(simpleGrantedAuthority);
//        }

        if (authorities != null) {
            return authorities;
        }
        //方式二   1.8语法完成
        //转换角色信息
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //转换角色信息
        authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))   //admin 进行拼接 ROLE_admin
                .collect(Collectors.toList());

        //authorities  ['ROLE_admin','system:user:list']
        return authorities;
    }

    /**
     * 获取用户密码,进行密码验证
     * @param
     * @return: java.lang.String
     */
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    /**
     * 获取用户名,进行身份验证
     * @param
     * @return: java.lang.String
     */
    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    /**
     * 用于判断用户的账户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用于判断用户的账户是否被锁定
     * @param
     * @return: boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用于判断用户凭证是否过期
     * @param
     * @return: boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用于判断用户是否已经激活
     * @param
     * @return: boolean
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
