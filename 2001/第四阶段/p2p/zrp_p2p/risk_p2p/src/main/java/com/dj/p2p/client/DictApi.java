package com.dj.p2p.client;

import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.dict.Dict;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "dict-service", path = "/dict/")
public interface DictApi {

    /**
     * 返回字典数据信息
     *
     * @return
     * @throws Exception
     */
    @PostMapping("returnDictMessage")
    ResultModel returnDictMessage() throws Exception;

}
