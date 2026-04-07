package com.fc.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.springsecurity.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID获取用户拥有的角色
     * @param id
     * @return: java.util.List<java.lang.String>
     */
    List<String> selectRolesByUserId(Long id);
}
