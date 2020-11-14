package com.dj.mall.auth.pro.entity.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_auth_resource")
public class ResourceEntity {
    /** 资源ID */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 资源名 */
    private String resourceName;

    /** url路径 */
    private String url;

    /** 父级ID */
    private Integer parentId;

    /** 资源类型 */
    private Integer resourceType;

    /** 资源码：纯大写 */
    private String resourceCode;
}
