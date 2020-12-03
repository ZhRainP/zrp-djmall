package com.dj.p2p.riskcontrol;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class P2pRecheck implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目ID
     */
    private Integer proId;

    /**
     * 提前还款违约金
     */
    private BigDecimal prePenalty;

    /**
     * 违约金
     */
    private BigDecimal penalSum;

    /**
     * 逾期罚息
     */
    private BigDecimal payment;

    /**
     * 借款存续期手续费计算方式
     */
    private BigDecimal renFeeType;

    /**
     * 借款存续期手续费
     */
    private BigDecimal renFee;

    /**
     * 筹款时间
     */
    private Integer fundraisTime;

    /**
     * 风控建议
     */
    private String riskOpi;

    /**
     * 发售时间
     */
    private Date releaseTime;

    /**
     * 借款合同
     */
    private String loanContract;
    /**
     * 审批结果 同意与拒绝
     */
    @TableField(exist = false)
    private String trialType;

    /**
     * 审核意见
     */
    @TableField(exist = false)
    private String exaOpi;

}
