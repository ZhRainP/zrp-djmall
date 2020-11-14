package com.dj.mall.auth.pro.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("djmall_auth_user")
public class UserEntity {
    /** 用户ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 盐 */
    private String salt;
    /** 用户级别 */
    private Integer level;
    /** 昵称 */
    private String nickName;
    /** 手机号 */
    private String phone;
    /** 邮箱 */
    private String mail;
    /** 性别 */
    private String sex;
    /** 状态 */
    private String status;
    /** 注册时间 */
    private LocalDateTime startTime;
    /** 最后登陆时间 */
    private LocalDateTime endTime;
    /** 姓名/手机号/邮箱模糊 */
    @TableField(exist = false)
    private String nameLike;

}
