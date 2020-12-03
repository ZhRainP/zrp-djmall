package com.dj.p2p.pojo.logincount;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ldm
 * @since 2020-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginCount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;


}
