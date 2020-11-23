package com.dj.mall.order.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.dozer.Mapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单明细
 */
@Data
@Accessors(chain = true)
@TableName("djmall_order_detail")
public class OrderDetail {

    /**
     * 明细ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("orderDetailId")
    private Integer id;

    /**
     * 父订单号
     */
    private String parentOrderNo;

    /**
     * 子订单号
     */
    private String childOrderNo;

    /**
     * 买家ID
     */
    private Integer buyerId;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商户ID
     */
    private Integer businessId;

    /**
     * skuID-针对再次购买时使用
     */
    private Integer skuId;

    /**
     * SKU信息(iphone-红色-64G)
     */
    private String skuInfo;

    /**
     * SKU价格
     */
    private BigDecimal skuPrice;

    /**
     * SKU折扣
     */
    private Integer skuRate;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 支付金额（含运费）
     */
    private BigDecimal payMoney;

    /**
     * 运费
     */
    private BigDecimal freightPrice;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
