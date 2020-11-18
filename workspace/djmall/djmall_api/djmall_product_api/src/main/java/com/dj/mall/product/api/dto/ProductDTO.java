package com.dj.mall.product.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductDTO implements Serializable {
    /**
     * 商品ID
     */
    private Integer id;
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
     * 商品描述
     */
    private String productDescription;
    /**
     * sku集合
     */
    private List<ProductSkuDTO> skuList;
    /**
     * 分页-每页几条
     */
    private Integer pageNo;
    /**
     * 总页数
     */
    private Integer pageSize;
    /**
     * 商品状态
     */
    private Integer productStatus;

    /**
     * 商品图片字节
     */
    private byte[] image;

    /**
     * 商品图图片地址
     */
    private String imgUrl;

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
}
