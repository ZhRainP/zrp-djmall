package com.dj.mall.admin.vo.sku;

import lombok.Data;

import java.util.List;

@Data
public class SkuResp {
    /**
     * 通用SKU关系ID
     */
    private Integer id;

    /**
     * 商品类型
     */
    private String productType;

    /**
     * 属性ID
     */
    private Integer attrId;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 字典code
     */
    private String code;
    /**
     * 属性值
     */
    private String attrValue;

    private List<Integer> ids;
}
