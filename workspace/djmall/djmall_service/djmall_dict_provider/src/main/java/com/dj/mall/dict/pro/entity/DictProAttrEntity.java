package com.dj.mall.dict.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 商品属性表
 */
@Data
@TableName("djmall_dict_product_attr")
public class DictProAttrEntity {

    /**
     * 商品属性ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品属性名称
     */
    private String attrName;



}
