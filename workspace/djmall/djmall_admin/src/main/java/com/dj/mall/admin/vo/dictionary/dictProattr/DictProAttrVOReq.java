package com.dj.mall.admin.vo.dictionary.dictProattr;

import lombok.Data;

@Data
public class DictProAttrVOReq {
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
