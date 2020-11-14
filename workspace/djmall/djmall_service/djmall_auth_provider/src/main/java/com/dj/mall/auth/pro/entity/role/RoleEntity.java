package com.dj.mall.auth.pro.entity.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_auth_role")
public class RoleEntity {
    /** 角色ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 角色名 */
    private String roleName;
}
