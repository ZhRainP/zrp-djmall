package com.dj.p2p.pojo.subject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
public class Check implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 检查表
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目ID
     */
    private Integer subjectId;

    /**
     * 提前还款违约金
     */
    private BigDecimal advanceRepayPenalty;

    /**
     * 违约金
     */
    private BigDecimal breachContract;

    /**
     * 逾期罚息
     */
    private BigDecimal overdueFine;

    /**
     * 借款存续期手续费计算方式
     */
    private String borrowDurationPoundageType;

    /**
     * 借款存续期手续费
     */
    private BigDecimal borrowDurationPoundage;

    /**
     * 筹款时间
     */
    private Integer fundraiseTime;

    /**
     * 风控建议
     */
    private String riskAdvice;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 借款合同
     */
    private String borrowContract;

    /**
     * 审核意见
     */
    @TableField(exist = false)
    private String checkOpinion;

    /**
     * 审批类型
     */
    @TableField(exist = false)
    private String checkType;

    /**
     * 审批结果
     */
    @TableField(exist = false)
    private String auditStatus;


}
