package com.dj.p2p.client;

import com.dj.p2p.common.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class UserApiFallBack implements UserApi {

    /**
     * 返回充值页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel securityCenter(String token) throws Exception {
        log.info("进入返回充值页面信息方法，服务降级");
        return null;
    }

    /**
     * 返回用户银行卡号
     *
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    @Override
    public String returnBankNumber(String token) throws Exception {
        log.info("进入返回用户银行卡号方法，服务降级");
        return null;
    }

    /**
     * 返回支付密码
     *
     * @param token 用户token
     * @return
     * @throws Exception
     */
    @Override
    public String returnPayPassword(String token) throws Exception {
        log.info("进入返回支付密码方法，服务降级");
        return null;
    }
}
