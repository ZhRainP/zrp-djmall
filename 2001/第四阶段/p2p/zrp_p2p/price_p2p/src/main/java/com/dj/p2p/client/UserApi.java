package com.dj.p2p.client;

import com.dj.p2p.common.ResultModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.POST;


@FeignClient(name = "user-service", path = "/user/", fallback = UserApiFallBack.class)
public interface UserApi {

    /**
     * 返回信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping("securityCenter")
    ResultModel securityCenter(@RequestHeader("token") String token) throws Exception;

    /**
     * 返回用户银行卡号
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    @PostMapping("returnWithdrawMessage")
    String returnBankNumber(@RequestHeader("token")String token) throws Exception;

    /**
     * 返回支付密码
     * @param token 用户token
     * @return
     * @throws Exception
     */
    @PostMapping("returnPayPassword")
    String returnPayPassword (@RequestHeader("token")String token) throws Exception;

}
