package com.dj.mall.platform.vo.user;

import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserVoReq {
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
     * 角色名称
     */
    private String roleName;
}
