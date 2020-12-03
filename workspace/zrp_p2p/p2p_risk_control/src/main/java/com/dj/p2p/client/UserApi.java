package com.dj.p2p.client;

import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "/user/", fallback = DictApiFullBack.class)
public interface UserApi {
//    @PostMapping("findBorrowerByUserId")
//    ResultModel findBorrowerByUserId(Integer userId) throws BusinessException;


    @PostMapping("findAllBorrower")
    ResultModel findAllBorrower() throws BusinessException;

    @PostMapping("findBorrowerByUserId")
    ResultModel findBorrowerByUserId(@RequestParam("userId") Integer userId) throws BusinessException;
}
