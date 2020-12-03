package com.dj.p2p.riskcontrol;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@TableName("p2p_financial")
public class RiskControl {
    /**
     * 主键
     */
    private Integer id;
    /**
     *标编号
     */
    private String proNo;
    /**
     *项目名称
     */
    private String proName;
    /**
     *产品类型
     */
    private String proType;
    /**
     *是否显示标的
     */
    private String isShowTarget;
    /**
     *标的类型
     */
    private String proTargetType;
    /**
     *借款人ID
     */
    private Integer borrowerId;
    /**
     *金额
     */
    private BigDecimal proMoney;
    /**
     *已筹金额
     */
    private BigDecimal proHadMoney;
    /**
     *单人是否限额
     */
    private Integer isLimit;
    /**
     *单人限额
     */
    private String proLimitMoney;
    /**
     *年利率
     */
    private BigDecimal proApr;
    /**
     *期限
     */
    private String proDeadline;
    /**
     *还款方式
     */
    private String proRepType;
    /**
     *借款说明
     */
    private String proExp;
    /**
     *标的审核状态
     */
    private String proStatus;
    /**
     * 发布时间
     */
//    private LocalDateTime proStartTime;
    /**
     * 起投金额
     */
    private BigDecimal proCaseMoney;

    /**
     * 利息
     */
    @TableField(exist = false)
    private BigDecimal interestMoney;
    /**
     * 本息
     */
    @TableField(exist = false)
    private BigDecimal interestANDPrincipal;
}
