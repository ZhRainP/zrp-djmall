package com.dj.mall.platform.vo;

import lombok.Data;

@Data
public class UserTokenVOResp {
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
