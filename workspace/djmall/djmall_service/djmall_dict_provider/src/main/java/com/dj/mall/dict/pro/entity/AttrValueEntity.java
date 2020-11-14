package com.dj.mall.dict.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_dict_product_attr_value")
public class AttrValueEntity {

    /**
     * 商品属性值ID
     */
    @TableId(type = IdType.AUTO)
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
