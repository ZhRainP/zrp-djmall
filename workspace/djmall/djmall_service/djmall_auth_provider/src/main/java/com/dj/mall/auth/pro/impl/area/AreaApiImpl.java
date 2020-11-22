package com.dj.mall.auth.pro.impl.area;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.pro.bo.AreaBo;
import com.dj.mall.auth.pro.entity.area.AreaEntity;
import com.dj.mall.auth.pro.mapper.area.AreaMapper;
import com.dj.mall.autr.api.area.AreaApi;
import com.dj.mall.autr.api.dto.area.AreaDTO;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;

import java.util.List;

@Service
public class AreaApiImpl extends ServiceImpl<AreaMapper, AreaEntity> implements AreaApi {

    @Override
    public List<AreaDTO> cascadeList(Integer pId) throws BusinessException {
        List<AreaBo> cascadeList = super.baseMapper.cascadeList(pId);
        return DozerUtil.mapList(cascadeList, AreaDTO.class);
    }
}
