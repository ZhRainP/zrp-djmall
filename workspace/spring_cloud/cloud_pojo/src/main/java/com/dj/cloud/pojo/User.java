package com.dj.cloud.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("djmall_auth_user")
public class User {
    private Integer id;
    private String username;
    private String password;
}
