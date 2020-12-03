package com.dj.p2p.client;

import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "dict-service", path = "/dict/", fallback = DictApiFullBack.class)
public interface DictApi {
    @PostMapping("toBid")
    ResultModel toBid() throws BusinessException;

    @PostMapping("toFindContract")
    ResultModel toFindContract() throws BusinessException;

    @PostMapping("tolenders")
    ResultModel tolenders)() throws BusinessException;
}
