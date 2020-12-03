package com.dj.p2p.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("p2p_user_balance")
public class UserWallet {
    /**
     * 钱包主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 冻结余额
     */
    private BigDecimal freezeBalance;

    /**
     * 可用余额
     */
    private BigDecimal balance;
}
