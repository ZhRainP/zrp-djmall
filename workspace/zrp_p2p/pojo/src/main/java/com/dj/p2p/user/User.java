package com.dj.p2p.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("p2p_user")
public class User {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     *手机号
     */
    private String phone;
    /**
     *密码
     */
    private String password;
    /**
     *性别
     */
    private String sex;
    /**
     *年龄
     */
    private Integer age;
    /**
     *学历
     */
    private String education;
    /**
     *婚姻
     */
    private String maritalStatus;
    /**
     *工作年限
     */
    private String workExperience;
    /**
     *资产估计
     */
    private BigDecimal assetValuation;
    /**
     *车产
     */
    private String carProperty;
    /**
     *房产
     */
    private String houseProperty;
    /**
     *年收入
     */
    private String annualIncome;
    /**
     *级别
     */
    private String level;
    /**
     *状态：锁定/未锁定
     */
    private String status;

    /**
     * 真实姓名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 最后登陆时间
     */
    @TableField(exist = false)
    private String loginTimeShow;

    /**
     * 登录次数
     */
    @TableField(exist = false)
    private Integer loginCount;
    /**
     * 开户状态
     */
    @TableField(exist = false)
    private String openStatus;
    /**
     * 确认交易密码
     */
    @TableField(exist = false)
    private String confirmPassword;

}
