package com.dj.p2p.pojo.openaccount;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ldm
 * @since 2020-11-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OpenAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开户信息ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 银行卡号
     */
    private String bankNumber;

    /**
     * 银行类型
     */
    private String bankType;

    /**
     * 银行卡是否绑定
     */
    private String bindBank;

    /**
     * 虚拟账号
     */
    private String virtualCard;

    /**
     * 银行预留手机号
     */
    private String reservedPhone;

    /**
     * 交易密码
     */
    private String payPassword;

    /**
     * 重复交易密码
     */
    @TableField(exist = false)
    private String repeatPayPassword;

    /**
     * 用户身份
     */
    @TableField(exist = false)
    private String level;

    /**
     * 手机号
     */
    @TableField(exist = false)
    private String phone;


}
