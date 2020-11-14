package com.dj.mall.admin.vo.dictionary.attrvalue;

import lombok.Data;

@Data
public class AttrValueVOResp {
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
