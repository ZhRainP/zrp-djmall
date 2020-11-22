package com.dj.mall.auth.pro.entity.shopcar;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_shop_car")
public class CarEntity {
    /**
     * 购物车ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 购买数量
     */
    private Integer count;
    /**
     * 商品ID
     */
    private Integer productId;
    /**
     * sku ID
     */
    private Integer skuId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 是否勾选
     */
    private Integer checkStatus;
}
