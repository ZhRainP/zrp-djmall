package com.dj.p2p.client;

import com.dj.p2p.pojo.dict.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class DictApiFallBack implements DictApi {

    /**
     * 根据父级CODE返回字典数据
     *
     * @param parentCode 父级CODE
     * @return
     * @throws Exception
     */
    @Override
    public List<Dict> selectDictByParentCode(String parentCode) throws Exception {
        log.info("进入根据父级CODE返回字典数据方法，服务降级");
        return null;
    }
}
