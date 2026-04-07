package com.fc.springsecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fc.springsecurity.entity.LoginUser;
import com.fc.springsecurity.entity.SysUser;
import com.fc.springsecurity.mapper.MenuMapper;
import com.fc.springsecurity.mapper.RoleMapper;
import com.fc.springsecurity.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 实现从数据库根据用户名检索用户信息
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);

        //如果查询不到数据,抛出异常,给出提示
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //todo 查询用户权限信息,添加到LoginUser中
//        ArrayList<String> list = new ArrayList<>(Arrays.asList("test"));
        //从数据库查询用户权限信息，保存到LoginUser
        List<String> perms = menuMapper.selectPermsByUserId(sysUser.getUserId());

        //todo  获取当前用户的角色信息
        List<String> roles = roleMapper.selectRolesByUserId(sysUser.getUserId());

        //方法的返回值是UserDetails类型，需要返回自定义的实体类，并且将user信息通过构造方法传入
        return new LoginUser(sysUser,perms,roles);
    }
}
