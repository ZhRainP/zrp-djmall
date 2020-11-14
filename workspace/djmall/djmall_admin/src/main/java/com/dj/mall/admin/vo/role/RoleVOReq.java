package com.dj.mall.admin.vo.role;

import lombok.Data;

import java.util.List;

@Data
public class RoleVOReq {
    /** 角色ID */
    private Integer id;
    /** 角色名 */
    private String roleName;
    /** 资源ID集合 */
    private List<Integer> resourceIds;
}
