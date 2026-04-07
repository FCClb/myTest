package com.fc.springboot_demo01.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class user {

    private String name;        // 用户名

    private String password;    // 密码

    private String gender;      // 性别

    private List<String> hobbies; // 爱好（复选框是多个值，用List接收）

    private String country;     // 国家

}
