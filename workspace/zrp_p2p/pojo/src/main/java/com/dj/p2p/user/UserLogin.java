package com.dj.p2p.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@TableName("p2p_user_login")
public class UserLogin {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 登陆时间
     */
    private LocalDateTime loginTime;

    @TableField(exist = false)
    private String loginTimeShow;

    public String getLoginTimeShow() {
        if(loginTime == null){
            return "该用户没有登陆记录";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(loginTime);
        return format;
    }



}
