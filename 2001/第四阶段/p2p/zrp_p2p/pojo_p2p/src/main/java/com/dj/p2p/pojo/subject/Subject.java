package com.dj.p2p.pojo.subject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发标表ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标编号
     */
    private String subjectNo;

    /**
     * 项目名称
     */
    private String productName;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 是否显示标的
     */
    private String isLookSubject;

    /**
     * 标的类型
     */
    private String subjectType;

    /**
     * 借款人ID
     */
    private Integer borrowerId;

    /**
     * 金额
     */
    private BigDecimal subjectMoney;

    /**
     * 已筹金额
     */
    private BigDecimal raiseMoney;

    /**
     * 单人是否限额
     */
    private String isLimit;

    /**
     * 单人限额
     */
    private BigDecimal subjectLimitMoney;

    /**
     * 年利率
     */
    private BigDecimal yearInterest;

    /**
     * 期限
     */
    private Integer timeLimit;

    /**
     * 还款方式
     */
    private String payBackMoneyType;

    /**
     * 借款说明
     */
    private String borrowAdvice;

    /**
     * 加入人次
     */
    private Integer subjectJoinNum;

    /**
     * 标的审核状态
     */
    private String proAuditStatus;

    /**
     * 是否签约
     */
    private String proIsSign;

    /**
     * 标状态
     */
    private String proStatus;

    /**
     * 发起时间
     */
    private LocalDateTime proStartTime;

    /**
     * 更新时间
     */
    private LocalDateTime proUpdateTime;

    /**
     * 真实姓名
     */
    @TableField(exist = false)
    private String realName;

    /**
     * 利息
     */
    @TableField(exist = false)
    private BigDecimal interest;

    /**
     * 本息合计
     */
    @TableField(exist = false)
    private BigDecimal interestAndPrincipal;

    /**
     * 创建时间
     */
    @TableField(exist = false)
    private String startTimeShow;

    /**
     * 性别：男（MAN）/女（WOMAN）
     */
    @TableField(exist = false)
    private String sex;

    /**
     * 学历：小学（PRIMARY_SCHOOL）/初中（MIDDLE_SCHOOL）/高中（HIGH_SCHOOL）/专科（JUNIOR_COLLEGE）
     * /本科（UNDERGRADUATE）/研究生及以上（GRADUATE_ABOVE）
     */
    @TableField(exist = false)
    private String education;

    /**
     * 婚姻：已婚（MARRI）/未婚（UNMARRI）
     */
    @TableField(exist = false)
    private String marriage;

    /**
     * 工作年限：1-3年（ONE_TO_THREE）/3-5年（THREE_TO_FIVE）/5-8年（FIVE_TO_EIGHT）/8年及以上（EIGHT_UP）
     */
    @TableField(exist = false)
    private String workYear;

    /**
     * 房产：全款买房（ALL_BUY_HOME）/贷款买房（NOT_ALL_BUY_HOME）/租房（RENT_HOUSE）/无（NO_HOME）
     */
    @TableField(exist = false)
    private String homeProduction;

    /**
     * 年收入：1-5万（ONE_TO_FIVE）/5-10万（FIVE_TO_TEN）/10-20万（TEN_TO_TWENTY）/20-30万（TWENTY_TO_THIRTY）
     * /30-50万（THIRTY_TO_FIFTY）/50-100万（FIFTY_TO_ONE_MILLION）/100万以上（ONE_MILLION_UP）
     */
    @TableField(exist = false)
    private String yearPrice;

    /**
     * 车产：全款购车（ALL_BUY_CAR）/贷款购车（NOT_ALL_BUY_CAR）/无车（NO_CAR）
     */
    @TableField(exist = false)
    private String carProduction;

    /**
     * 年龄
     */
    @TableField(exist = false)
    private Integer age;

    /**
     * 资产估值
     */
    @TableField(exist = false)
    private BigDecimal assetsValuation;

    /**
     * 凑款进度
     */
    @TableField(exist = false)
    private BigDecimal raiseSchedule;

    /**
     * 放款总额
     */
    @TableField(exist = false)
    private BigDecimal allPrice;


    public String getStartTimeShow() {
        if(proStartTime == null){
            return "还未有创建时间";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(proStartTime);
        return format;
    }
}
