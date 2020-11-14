package com.dj.mall.admin.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class UserVoResp {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 确认密码
     */
    private String confirmPassword;
    /**
     * 盐
     */
    private String salt;
    /**
     * 用户级别
     */
    private Integer level;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 性别
     */
    private String sex;
    /**
     * 状态
     */
    private String status;
    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime startTime;
    /**
     * 最后登陆时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime endTime;
    /**
     * 角色名称
     */
    private String roleName;
}
