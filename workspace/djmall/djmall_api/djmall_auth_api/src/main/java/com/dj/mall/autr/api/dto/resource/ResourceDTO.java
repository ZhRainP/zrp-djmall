package com.dj.mall.autr.api.dto.resource;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResourceDTO implements Serializable {

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

    /** 资源ID集合 */
    private List<Integer> resourceIds;
}
