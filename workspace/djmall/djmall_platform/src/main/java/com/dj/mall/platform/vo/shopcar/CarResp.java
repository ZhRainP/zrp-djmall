package com.dj.mall.platform.vo.shopcar;

import lombok.Data;

@Data
public class CarResp {
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
