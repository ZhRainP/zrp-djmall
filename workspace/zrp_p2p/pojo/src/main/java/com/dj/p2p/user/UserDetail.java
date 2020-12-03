package com.dj.p2p.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@TableName("p2p_user_detail")
@Accessors(chain = true)
public class UserDetail {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 客户名
     */
    private String userName;
    /**
     * 证件类型
     */
    private String idCardType;
    /**
     *证件号
     */
    private String idCard;
    /**
     *银行卡号
     */
    private String bankCard;
    /**
     *账户类型[投资人，借款人]
     */
    private String accountType;
    /**
     *银行预留手机号
     */
    private String bankPhone;
    /**
     *交易密码
     */
    private String transactionPwd;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 虚拟账号
     */
    private String virtualCard;
    /**
     * 开户状态
     */
    private String openStatus;
    /**
     * 认证状态
     */
    private String approveStatus;

    /**
     * 确认交易密码
     */
    @TableField(exist = false)
    private String confirmTransactionPwd;

    /**
     * 绑定手机号
     */
    @TableField(exist = false)
    private String phone;
    /**
     * 余额
     */
    @TableField(exist = false)
    private BigDecimal balance;

    @TableField(exist = false)
    private Integer age;
    @TableField(exist = false)
    private String education;
    @TableField(exist = false)
    private String sex;
    @TableField(exist = false)
    private String maritalStatus;
    @TableField(exist = false)
    private String workExperience;
    @TableField(exist = false)
    private String houseProperty;
    @TableField(exist = false)
    private String carProperty;
    @TableField(exist = false)
    private String annualIncome;
    @TableField(exist = false)
    private String assetValuation;
}
