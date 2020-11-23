package com.dj.mall.platform.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderVOResp {
    /**
     *订单号
     */
    private String orderNo;
    /**
     *买家ID
     */
    private Integer buyerId;
    /**
     *订单总金额
     */
    private BigDecimal totalMoney;
    /**
     *实付总金额
     */
    private BigDecimal totalPayMoney;
    /**
     *总运费
     */
    private BigDecimal totalFreight;
    /**
     *总购买数量
     */
    private Integer totalBuyCount;
    /**
     *支付方式
     */
    private String payType;
    /**
     *收货信息-省
     */
    private String receiverProvince;
    /**
     *收货信息-城市
     */
    private String receiverCity;
    /**
     *收货信息-县区
     */
    private String receiverCounty;
    /**
     *收货信息-收货人
     */
    private String receiverName;
    /**
     *收货信息-手机号
     */
    private String receiverPhone;
    /**
     *收货信息-地址明细
     */
    private String receiverDetail;
    /**
     *订单状态【已取消/待支付/代发货/已发货/确认收获/已完成】
     */
    private String orderStatus;
    /**
     *创建时间
     */
    private LocalDateTime createTime;
    /**
     *支付时间
     */
    private LocalDateTime payTime;
    /**
     *更新时间
     */
    private LocalDateTime updateTime;
}
