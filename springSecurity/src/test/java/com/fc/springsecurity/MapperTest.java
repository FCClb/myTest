package com.fc.springsecurity;

import com.fc.springsecurity.mapper.RoleMapper;
import com.fc.springsecurity.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testSelect() {

        sysUserMapper.selectList(null).forEach(System.out::println);

    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testBcryp() {

        //对原始密码进行加密
        String e1 = passwordEncoder.encode("123456");
        System.out.println(e1);
        //$2a$10$iN3SVAtD8UOO1CPh8rjC2.xqYjOjfGI7WejCQEtIuzVD.QZbaS92u

        //随机盐值对密码进行加密,所以每次的加密结果不一致,即使是相同的原始
        String e2 = passwordEncoder.encode("123456");
        System.out.println("相同密码加密比较:" + e1.equals(e2));

        //对比原始密码和加密后的密码
        boolean b = passwordEncoder.matches("123456", "$2a$10$iJSDuWI32kKxV0PH6vEsuO9sOtWxtD8glNYXxRp1AhWiTK2WgXdhW");

        System.out.println("用matches()对比原始密码和加密后的密码" + b);

    }

    @Test
    public void testRoleSelect() {

        List<String> strings = roleMapper.selectRolesByUserId(1L);
        System.out.println(strings);

    }
}
