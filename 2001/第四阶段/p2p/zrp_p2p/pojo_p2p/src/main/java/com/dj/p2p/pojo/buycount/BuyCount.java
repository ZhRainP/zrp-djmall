package com.dj.p2p.pojo.buycount;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author ldm
 * @since 2020-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BuyCount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购买记录表ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 购买人ID
     */
    private Integer buyerId;

    /**
     * 购买金额
     */
    private BigDecimal price;

    /**
     * 标的ID
     */
    private Integer subjectId;

    /**
     * 全部金额
     */
    @TableField(exist = false)
    private BigDecimal subjectMoney;

    /**
     * 年利率
     */
    @TableField(exist = false)
    private BigDecimal yearInterest;

    /**
     * 利息
     */
    @TableField(exist = false)
    private BigDecimal interest;

    /**
     * 本息合计
     */
    @TableField(exist = false)
    private BigDecimal interestAndSubjectMoney;
}
