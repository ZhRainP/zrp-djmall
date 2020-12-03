package com.dj.p2p.pojo.financial;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
@EqualsAndHashCode(callSuper = false)
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账单ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 账单编号
     */
    private String billNo;

    /**
     * 应还本息
     */
    private BigDecimal principalAndInterest;

    /**
     * 本金
     */
    private BigDecimal principal;

    /**
     * 利息
     */
    private BigDecimal interest;

    /**
     * 到期时间
     */
    private LocalDate overTime;

    /**
     * 还款时间
     */
    private LocalDateTime backTime;

    /**
     * 账单状态
     */
    private String billStatus;

    /**
     * 期数
     */
    private String timeNumber;

    /**
     * 标的ID
     */
    private Integer subjectId;

    /**
     * 最后还款时间展示
     */
    @TableField(exist = false)
    private String overTimeShow;

    /**
     * 待还金额
     */
    @TableField(exist = false)
    private BigDecimal allRepayPrice;

    public String getOverTimeShow() {
        if(overTime==null){
            return "无时间";
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = overTime.format(fmt);
        return dateStr;
    }

}
