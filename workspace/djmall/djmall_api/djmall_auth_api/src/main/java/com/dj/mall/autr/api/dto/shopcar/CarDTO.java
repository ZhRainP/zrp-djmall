package com.dj.mall.autr.api.dto.shopcar;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarDTO implements Serializable {
    /**
     * 购物车ID
     */
    private Integer id;
    /**
     * 购买数量
     */
    private Integer count;
    /**
     * 商品ID
     */
    private Integer productId;
    /**
     * sku ID
     */
    private Integer skuId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 是否勾选
     */
    private Integer checkStatus;
}
