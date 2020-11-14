package com.dj.mall.auth.pro.impl.resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.pro.bo.ResourceBo;
import com.dj.mall.auth.pro.entity.resource.ResourceEntity;
import com.dj.mall.auth.pro.mapper.resource.ResourceMapper;
import com.dj.mall.auth.pro.service.role.RoleResourceService;
import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.autr.api.resouce.ResourceApi;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceApiImpl extends ServiceImpl<ResourceMapper, ResourceEntity> implements ResourceApi {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceService roleResourceService;
    /**
     * 资源列表查询
     * @return
     * @throws BusinessException
     */
    @Override
    public List<ResourceDTO> findList() throws BusinessException {
        List<ResourceEntity> resourceList = super.list();
        return DozerUtil.mapList(resourceList, ResourceDTO.class);
    }

    /**
     * 添加资源
     * @param resourceDTO
     * @throws BusinessException
     */
    @Override
    public void insertResource(ResourceDTO resourceDTO) throws BusinessException {
        super.save(DozerUtil.map(resourceDTO, ResourceEntity.class));
    }

    /**
     * 通过ID查询父节点名称
     * @param pId
     * @return
     * @throws BusinessException
     */
    @Override
    public ResourceDTO findResourceById(Integer pId) throws BusinessException {
        ResourceEntity resourceEntity = super.getById(pId);
        return DozerUtil.map(resourceEntity, ResourceDTO.class);
    }

    /**
     * 更新资源
     * @param map
     * @throws BusinessException
     */
    @Override
    public void updateResource(ResourceDTO map) throws BusinessException {
        super.updateById(DozerUtil.map(map, ResourceEntity.class));
    }

    @Override
    public List<ResourceDTO> findAllList(Integer id) throws BusinessException {
        List<ResourceBo> resourceList = resourceMapper.findAllList(id);
        return DozerUtil.mapList(resourceList, ResourceDTO.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteResource(ResourceDTO resourceDTO) throws BusinessException {
        //删除资源关系
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.in("resource_id", resourceDTO.getResourceIds());
        roleResourceService.remove(wrapper);
        //删除资源
        super.removeByIds(resourceDTO.getResourceIds());
    }

}
