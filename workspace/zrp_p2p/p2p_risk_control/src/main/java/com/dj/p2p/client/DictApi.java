package com.dj.p2p.client;

import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.dict.Dictionary;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "dict-service", path = "/dict/", fallback = DictApiFullBack.class)
public interface DictApi {
    @PostMapping("toBid")
    ResultModel toBid() throws BusinessException;

    @PostMapping("toFindContract")
    ResultModel toFindContract() throws BusinessException;
}
