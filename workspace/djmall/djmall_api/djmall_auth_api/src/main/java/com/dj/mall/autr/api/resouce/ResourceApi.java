package com.dj.mall.autr.api.resouce;

import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.common.base.BusinessException;

import java.util.List;

public interface ResourceApi {
    /**
     * 列表查询
     * @return
     * @throws BusinessException
     */
    List<ResourceDTO> findList() throws BusinessException;

    /**
     * 添加资源
     * @param resourceDTO
     * @throws BusinessException
     */
    void insertResource(ResourceDTO resourceDTO) throws BusinessException;

    /**
     * id查找父节点名字
     * @param pId
     * @return
     * @throws BusinessException
     */
    ResourceDTO findResourceById(Integer pId) throws BusinessException;

    /**
     * 修改资源
     * @param map
     * @throws BusinessException
     */
    void updateResource(ResourceDTO map) throws BusinessException;

    /**
     * 左侧菜单列表
     * @return
     * @throws BusinessException
     */
    List<ResourceDTO> findAllList(Integer id) throws BusinessException;

    /**
     * 删除资源
     * @param resourceDTO
     * @throws BusinessException
     */
    void deleteResource(ResourceDTO resourceDTO) throws BusinessException;
}
