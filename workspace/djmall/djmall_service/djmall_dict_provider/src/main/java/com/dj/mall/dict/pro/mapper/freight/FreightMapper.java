package com.dj.mall.dict.pro.mapper.freight;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.pro.entity.FreightEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface FreightMapper extends BaseMapper<FreightEntity> {
    List<FreightEntity> list() throws DataAccessException;
}
