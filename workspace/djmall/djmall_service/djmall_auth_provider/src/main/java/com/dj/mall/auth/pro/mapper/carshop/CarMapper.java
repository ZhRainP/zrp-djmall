package com.dj.mall.auth.pro.mapper.carshop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.pro.bo.CarBO;
import com.dj.mall.auth.pro.entity.shopcar.CarEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CarMapper extends BaseMapper<CarEntity> {
    List<CarBO> findCarById(Integer id) throws DataAccessException;
}
