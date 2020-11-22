package com.dj.mall.auth.pro.mapper.area;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.pro.bo.AreaBo;
import com.dj.mall.auth.pro.entity.area.AreaEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface AreaMapper extends BaseMapper<AreaEntity> {
    List<AreaBo> cascadeList(Integer pId) throws DataAccessException;
}
