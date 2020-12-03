package com.dj.p2p.pojo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别：男（MAN）/女（WOMAN）
     */
    private String sex;

    /**
     * 学历：小学（PRIMARY_SCHOOL）/初中（MIDDLE_SCHOOL）/高中（HIGH_SCHOOL）/专科（JUNIOR_COLLEGE）
     * /本科（UNDERGRADUATE）/研究生及以上（GRADUATE_ABOVE）
     */
    private String education;

    /**
     * 婚姻：已婚（MARRI）/未婚（UNMARRI）
     */
    private String marriage;

    /**
     * 工作年限：1-3年（ONE_TO_THREE）/3-5年（THREE_TO_FIVE）/5-8年（FIVE_TO_EIGHT）/8年及以上（EIGHT_UP）
     */
    private String workYear;

    /**
     * 房产：全款买房（ALL_BUY_HOME）/贷款买房（NOT_ALL_BUY_HOME）/租房（RENT_HOUSE）/无（NO_HOME）
     */
    private String homeProduction;

    /**
     * 年收入：1-5万（ONE_TO_FIVE）/5-10万（FIVE_TO_TEN）/10-20万（TEN_TO_TWENTY）/20-30万（TWENTY_TO_THIRTY）
     * /30-50万（THIRTY_TO_FIFTY）/50-100万（FIFTY_TO_ONE_MILLION）/100万以上（ONE_MILLION_UP）
     */
    private String yearPrice;

    /**
     * 车产：全款购车（ALL_BUY_CAR）/贷款购车（NOT_ALL_BUY_CAR）/无车（NO_CAR）
     */
    private String carProduction;

    /**
     * 级别：借款人（BORROWER）/投资人（INVESTORS）/风控专员（RISK_CONTROL_SPECIALIST）/风控经理（RISK_CONTROL_MANAGER）/风控总监（DIRECTOR_RISK_CONTROL）
     */
    private String level;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 资产估值
     */
    private BigDecimal assetsValuation;

    /**
     * 用户状态
     */
    private String userStatus;

    /**
     * 登录计数
     */
    @TableField(exist = false)
    private Integer loginCount;

    /**
     * 是否锁定
     */
    private String isLock;

    /**
     * 登录时间
     */
    @TableField(exist = false)
    private LocalDateTime loginTime;

    /**
     * 真实姓名
     */
    @TableField(exist = false)
    private String realName;

    /**
     * 用户状态名称
     */
    @TableField(exist = false)
    private String userStatusName;

    /**
     * 用户锁定名称
     */
    @TableField(exist = false)
    private String lockName;

    /**
     * 登录时间展示
     */
    @TableField(exist = false)
    private String loginTimeShow;

    /**
     * 虚拟卡号
     */
    @TableField(exist = false)
    private String virtualCard;

    /**
     * 身份证
     */
    @TableField(exist = false)
    private String idCard;

    /**
     * 绑定银行卡号
     */
    @TableField(exist = false)
    private String bankNumber;

    /**
     * 重复密码
     */
    @TableField(exist = false)
    String repeatPassword;

    public String getLoginTimeShow() {
        if(loginTime == null){
            return "该用户没有登陆记录";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(loginTime);
        return format;
    }

}
