package com.dj.mall.autr.api.role;

import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.autr.api.dto.role.RoleDTO;
import com.dj.mall.autr.api.dto.ZTreeData;
import com.dj.mall.common.base.BusinessException;

import java.util.List;

public interface RoleApi {
    /**
     * 列表展示
     * @return
     * @throws BusinessException
     */
    List<RoleDTO> findList() throws BusinessException;

    /**
     * 添加角色
     * @param roleDTO
     * @throws BusinessException
     */
    void insertRole(RoleDTO roleDTO) throws BusinessException;

    /**
     * 根据id查找角色信息
     * @param id id
     * @return
     * @throws BusinessException
     */
    RoleDTO findRoleById(Integer id) throws BusinessException;

    /**
     * 修改资源
     * @param map
     * @throws BusinessException
     */
    void updateRole(RoleDTO map) throws BusinessException;

    /**
     * 查询关联资源
     * @param id
     * @return
     */
    List<ZTreeData> getRoleResource(Integer id) throws BusinessException;

    /**
     * 保存关联资源
     * @param map
     * @throws BusinessException
     */
    void savaRoleResource(RoleDTO map) throws BusinessException;

    /**
     * 根据角色Id获取资源信息
     * @param roleId 角色ID
     * @return
     * @throws BusinessException
     */
    List<ResourceDTO> getRoleResourceByRoleId(Integer roleId) throws BusinessException;
}
