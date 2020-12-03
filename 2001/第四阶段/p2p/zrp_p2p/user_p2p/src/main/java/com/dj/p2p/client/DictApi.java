package com.dj.p2p.client;

import com.dj.p2p.pojo.dict.Dict;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "dict-service", path = "/dict/", fallback = DictApiFallBack.class)
public interface DictApi {

    @PostMapping("selectDictByParentCode")
    List<Dict> selectDictByParentCode(@RequestParam("parentCode") String parentCode)throws Exception;

}
