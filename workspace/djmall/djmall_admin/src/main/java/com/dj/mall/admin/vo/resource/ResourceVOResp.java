package com.dj.mall.admin.vo.resource;

import lombok.Data;

import java.util.List;

@Data
public class ResourceVOResp {

    /** 资源ID */
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
