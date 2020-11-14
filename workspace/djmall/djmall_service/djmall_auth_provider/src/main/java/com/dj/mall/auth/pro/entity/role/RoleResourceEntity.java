package com.dj.mall.auth.pro.entity.role;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_auth_role_resource")
public class RoleResourceEntity {
    /** 角色Id */
    private Integer roleId;

    /** 资源ID */
    private Integer resourceId;
}
