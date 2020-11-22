package com.dj.mall.autr.api.shopcar;

import com.dj.mall.autr.api.dto.shopcar.CarDTO;
import com.dj.mall.common.base.BusinessException;

public interface CarApi {
    /**
     * 添加购物车
     * @param carDTO 购物车信息
     * @throws BusinessException
     */
    void addShop(CarDTO carDTO) throws BusinessException;

    /**
     * 改变勾选状态
     * @param carId 购物车ID
     * @param checkStatus
     */
    void updateStatus(Integer carId, Integer checkStatus) throws BusinessException;

    /**
     * 后悔了不想要
     * @param carId 根据ID删除
     * @throws BusinessException
     */
    void delCarByCarId(Integer carId) throws BusinessException;
}
