package com.dj.mall.autr.api.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserTokenDTO implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     *用户名
     */
    private String username;
    /**
     *token
     */
    private String token;
    /**
     *昵称
     */
    private String nickName;
}
