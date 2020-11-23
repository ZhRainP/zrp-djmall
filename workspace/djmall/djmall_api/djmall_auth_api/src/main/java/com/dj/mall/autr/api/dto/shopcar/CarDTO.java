package com.dj.mall.autr.api.dto.shopcar;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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


    /**
     * 商品名
     */
    private String productName;
    /**
     * 商品邮费
     */
    private String productFreight;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * sku价格
     */
    private double skuPrice;

    /**
     * sku库存
     */
    private Integer skuCount;

    /**
     * sku折扣
     */
    private Integer skuRate;
    /**
     * sku属性名
     */
    private String skuName;

}
