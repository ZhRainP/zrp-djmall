package com.dj.mall.autr.api.area;

import com.dj.mall.autr.api.dto.area.AreaDTO;
import com.dj.mall.common.base.BusinessException;

import java.util.List;

public interface AreaApi {
    List<AreaDTO> cascadeList(Integer pId) throws BusinessException;
}
