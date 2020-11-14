package com.dj.mall.dict.api.dto.dictproattr;

import lombok.Data;

import java.io.Serializable;

@Data
public class DictProAttrDTO implements Serializable {
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
