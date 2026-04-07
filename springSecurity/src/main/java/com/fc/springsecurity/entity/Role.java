package com.fc.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表对应实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "sys_role")
public class Role implements Serializable {

    //角色ID
    @TableId
    private Long roleId;

    //角色名称
    private String roleName;

    //角色权限字符串
    private String roleKey;

    //角色状态 (0 正常, 1 停用)
    private String status;

    //删除标识 0 存在 ,1 删除
    private String delFlag;

    private String createBy;

    private String updateBy;

    private Date updateTime;

    private Date createTime;

    private String remark;
}
