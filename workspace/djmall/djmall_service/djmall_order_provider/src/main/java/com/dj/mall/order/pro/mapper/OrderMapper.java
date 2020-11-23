package com.dj.mall.order.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.order.pro.bo.CarOrderBO;
import com.dj.mall.order.pro.entity.OrderEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderEntity> {
    /**
     * 根据用户ID获取用户下单信息
     * @param buyerId 用户订单
     * @return
     * @throws DataAccessException
     */
    List<CarOrderBO> findCarOrderByUserId(Integer buyerId) throws DataAccessException;
}
