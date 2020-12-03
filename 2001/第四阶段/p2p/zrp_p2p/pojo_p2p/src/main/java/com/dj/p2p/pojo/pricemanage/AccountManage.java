package com.dj.p2p.pojo.pricemanage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper = false)
public class AccountManage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 金额数据ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 可用金额
     */
    private BigDecimal availablePrice;

    /**
     * 冻结金额
     */
    private BigDecimal amountPrice;

    /**
     * 总资产
     */
    @TableField(exist = false)
    private BigDecimal allPrice;

    /**
     * 总收益
     */
    @TableField(exist = false)
    private BigDecimal allRevenue;

    /**
     * 代收金额
     */
    @TableField(exist = false)
    private BigDecimal waitPrice;

    /**
     * 代还金额
     */
    @TableField(exist = false)
    private BigDecimal backPrice;

    /**
     * 充值金额
     */
    @TableField(exist = false)
    private BigDecimal price;

    /**
     * 支付密码
     */
    @TableField(exist = false)
    private String payPassword;

    /**
     * 标的ID
     */
    @TableField(exist = false)
    private Integer subjectId;

    /**
     * 最大可投金额
     */
    @TableField(exist = false)
    private BigDecimal maxBuyPrice;

    /**
     * 筹款进度
     */
    @TableField(exist = false)
    private BigDecimal progress;

}
