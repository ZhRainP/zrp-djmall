package com.dj.mall.dict.pro.bo;

import lombok.Data;

@Data
public class DictProAttrBO {
    /**
     * 商品属性ID
     */
    private Integer id;

    /**
     * 商品属性名称
     */
    private String attrName;

    /**
     * 属性值
     */
    private String attrValue;
}
