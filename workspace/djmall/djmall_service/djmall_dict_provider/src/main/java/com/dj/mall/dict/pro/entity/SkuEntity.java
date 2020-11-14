package com.dj.mall.dict.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_dict_product_sku_gm")
public class SkuEntity {
    /**
     * 通用SKU关系ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品类型
     */
    private String productType;

    /**
     * 属性ID
     */
    private Integer attrId;
}
