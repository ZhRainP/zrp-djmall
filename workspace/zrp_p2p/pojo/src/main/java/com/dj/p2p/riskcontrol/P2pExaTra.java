package com.dj.p2p.riskcontrol;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class P2pExaTra implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 审核人ID
     */
    private Integer exaUserId;

    /**
     * 项目ID
     */
    private Integer exaProId;

    /**
     * 审核意见
     */
    private String exaOpi;

    /**
     * 审批类型
     */
    private String exaType;

    /**
     * 审批时间
     */
    private Date exaTime;

    /**
     * 审批结果 同意与拒绝
     */
    @TableField(exist = false)
    private String trialType;

}
