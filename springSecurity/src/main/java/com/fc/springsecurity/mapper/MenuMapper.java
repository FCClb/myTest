package com.fc.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.springsecurity.entity.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    //根据用户id获取用户权限
    public List<String> selectPermsByUserId(Long id);

}
