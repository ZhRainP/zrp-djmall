package com.dj.p2p.pojo.subject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ldm
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 审核记录表ID
     */
    @TableId( type = IdType.AUTO)
    private Integer id;

    /**
     * 审核人ID
     */
    private Integer checkUserId;

    /**
     * 项目ID
     */
    private Integer subjectId;

    /**
     * 审核意见
     */
    private String checkOpinion;

    /**
     * 审批类型
     */
    private String checkType;

    /**
     * 审批时间
     */
    private LocalDateTime checkTime;

    /**
     * 审批结果
     */
    private String auditStatus;
}
