package com.dj.mall.admin.vo.product;

import lombok.Data;

@Data
public class ProductSKUVOReq {
    /**
     *SKUID
     */
    private Integer id;
    /**
     *商品ID
     */
    private Integer productId;
    /**
     *SKU价格
     */
    private Double skuPrice;
    /**
     *sku库存
     */
    private Integer skuCount;
    /**
     *SKU折扣,0表示无折扣
     */
    private Integer skuRate;
    /**
     *SKU状态[0下架,1上架]
     */
    private Integer skuStatus;
    /**
     *SKU属性值名集合[name1:name2]
     */
    private String skuName;
    /**
     *是否默认 1:是，0：不是
     */
    private Integer isDefault;
}
