package com.dj.mall.dict.api.dto.attrvalue;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrValueDTO implements Serializable {
    /**
     * 商品属性值ID
     */
    private Integer id;

    /**
     * 商品属性ID
     */
    private Integer attrId;

    /**
     * 属性值
     */
    private String attrValue;
}
