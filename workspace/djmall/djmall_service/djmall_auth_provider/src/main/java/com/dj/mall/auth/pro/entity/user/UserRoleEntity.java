package com.dj.mall.auth.pro.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_auth_user_role")
public class UserRoleEntity {
    /** 用户ID */
    private Integer userId;

    /** 角色ID */
    private Integer roleId;
}
