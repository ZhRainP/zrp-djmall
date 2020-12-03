package com.dj.p2p.client;

import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "dict-service", path = "/dict/", fallback = DictApiFullBack.class)
public interface DictApi {
    @PostMapping("findList")
    Map<String,Object> findList() throws BusinessException;

    @PostMapping("toOpenCount")
    ResultModel toOpenCount() throws BusinessException;
}
