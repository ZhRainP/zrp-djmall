package com.dj.p2p.client;

import com.dj.p2p.common.ResultModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "user-service", path = "/user/")
public interface UserApi {

    /**
     * 返回借款人信息
     *
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    @PostMapping("returnBorrower")
    ResultModel returnBorrower(@RequestHeader("token") String token) throws Exception;

    /**
     * 返回首页页面信息
     *
     * @param token 用户令牌
     * @return
     */
    @PostMapping("returnHomePageMessage")
    ResultModel returnHomePageMessage(@RequestHeader("token") String token);

}
