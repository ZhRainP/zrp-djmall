package com.dj.mall.autr.api.dto.role;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleDTO implements Serializable {
    /** 角色ID */
    private Integer id;
    /** 角色名 */
    private String roleName;
    /** 资源ID集合 */
    private List<Integer> resourceIds;
}
