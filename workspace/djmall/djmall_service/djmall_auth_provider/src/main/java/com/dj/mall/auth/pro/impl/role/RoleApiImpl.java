package com.dj.mall.auth.pro.impl.role;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.pro.entity.role.RoleEntity;
import com.dj.mall.auth.pro.entity.role.RoleResourceEntity;
import com.dj.mall.auth.pro.mapper.role.RoleMapper;
import com.dj.mall.auth.pro.service.role.RoleResourceService;
import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.autr.api.dto.role.RoleDTO;
import com.dj.mall.autr.api.dto.ZTreeData;
import com.dj.mall.autr.api.resouce.ResourceApi;
import com.dj.mall.autr.api.role.RoleApi;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.constant.CacheConstant;
import com.dj.mall.common.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleApiImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleApi {

    @Autowired
    private ResourceApi resourceApi;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 角色列表
     * @return
     * @throws BusinessException
     */
    @Override
    public List<RoleDTO> findList() throws BusinessException {
        List<RoleEntity> roleEntities = super.list();
        return DozerUtil.mapList(roleEntities, RoleDTO.class);
    }

    /**
     * 添加角色
     * @param roleDTO roleDto
     * @throws BusinessException
     */
    @Override
    public void insertRole(RoleDTO roleDTO) throws BusinessException {
        super.save(DozerUtil.map(roleDTO, RoleEntity.class));
    }

    /**
     * 根据Id角色信息
     * @param id 角色ID
     * @return
     * @throws BusinessException
     */
    @Override
    public RoleDTO findRoleById(Integer id) throws BusinessException {
        RoleEntity roleEntity = super.getById(id);
        return DozerUtil.map(roleEntity, RoleDTO.class);
    }

    /**
     * 修改角色
     * @param map
     * @throws BusinessException
     */
    @Override
    public void updateRole(RoleDTO map) throws BusinessException {
        super.updateById(DozerUtil.map(map, RoleEntity.class));
    }

    /**
     * 获取关联资源
     * @param id 角色ID
     * @return
     * @throws BusinessException
     */
    @Override
    public List<ZTreeData> getRoleResource(Integer id) throws BusinessException {
        //获取全部资源
        List<ResourceDTO> resourceDTOList = resourceApi.findList();
        //获取角色已关联资源
        QueryWrapper<RoleResourceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        List<RoleResourceEntity> roleResourceList = roleResourceService.list(wrapper);
        //根据上面两步设置checked的值
        ArrayList<ZTreeData> zTreeDataList = new ArrayList<>();
        for (ResourceDTO resource : resourceDTOList) {
            ZTreeData zTreeData = DozerUtil.map(resource, ZTreeData.class);
            for (RoleResourceEntity roleResource : roleResourceList) {
                if (resource.getId().equals(roleResource.getResourceId())){
                    zTreeData.setChecked(true);
                    break;
                }
            }
            zTreeDataList.add(zTreeData);
        }
        return zTreeDataList;
    }

    /**
     * 关联资源保存
     * @param map
     * @throws BusinessException
     */
    @Override
    public void savaRoleResource(RoleDTO map) throws BusinessException {
        //删除原来的角色资源关系
        QueryWrapper<RoleResourceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", map.getId());
        roleResourceService.remove(wrapper);

        //保存新的资源关系
        List<RoleResourceEntity> roleResourceEntityList = new ArrayList<>();
        for (Integer resourceId : map.getResourceIds()) {
            RoleResourceEntity roleResourceEntity = new RoleResourceEntity();
            roleResourceEntity.setRoleId(map.getId());
            roleResourceEntity.setResourceId(resourceId);
            roleResourceEntityList.add(roleResourceEntity);
        }
        roleResourceService.saveBatch(roleResourceEntityList);
        //更新缓存
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(CacheConstant.ROLE_ALL_KEY, CacheConstant.RILE_ID_PREFIX + map.getId(),
                getRoleResourceByRoleId(map.getId()));
    }

    /**
     * 根据角色Id获取资源信息
     * @param roleId 角色ID
     * @return
     * @throws BusinessException
     */
    @Override
    public List<ResourceDTO> getRoleResourceByRoleId(Integer roleId) throws BusinessException {
        return DozerUtil.mapList(getBaseMapper().getRoleResourceByRoleId(roleId), ResourceDTO.class);
    }


}
