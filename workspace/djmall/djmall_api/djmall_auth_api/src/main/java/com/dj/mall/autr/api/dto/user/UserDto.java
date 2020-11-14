package com.dj.mall.autr.api.dto.user;

import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto implements Serializable {
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
     * 盐
     */
    private String salt;
    /**
     * 确认密码
     */
    private String confirmPassword;
    /**
     * 用户级别
     */
    private Integer level;
    /**
     * 用户权限
     */
    private List<ResourceDTO> permissionList;
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
     * 姓名/手机号/邮箱模糊
     */
    private String nameLike;

    /**
     * 角色名称
     */
    private String roleName;
}
