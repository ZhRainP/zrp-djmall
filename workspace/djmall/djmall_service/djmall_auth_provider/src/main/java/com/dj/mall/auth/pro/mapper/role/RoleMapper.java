package com.dj.mall.auth.pro.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.pro.entity.role.RoleEntity;
import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 根据角色Id获取资源信息
     * @param roleId 角色ID
     * @return
     * @throws DataAccessException
     */
    List<ResourceDTO> getRoleResourceByRoleId(Integer roleId) throws DataAccessException;
}
