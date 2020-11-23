package com.dj.mall.order.api;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.order.api.dto.OrderDTO;

public interface OrderApi {
    /**
     * 提交订单
     * @param orderDTO 订单信息
     * @throws BusinessException
     */
    void insertOrder(OrderDTO orderDTO) throws BusinessException;
}
