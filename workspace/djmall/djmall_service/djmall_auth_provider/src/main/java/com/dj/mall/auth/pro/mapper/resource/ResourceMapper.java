package com.dj.mall.auth.pro.mapper.resource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.pro.bo.ResourceBo;
import com.dj.mall.auth.pro.entity.resource.ResourceEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ResourceMapper extends BaseMapper<ResourceEntity> {
    List<ResourceBo> findAllList(Integer id) throws DataAccessException;
}
