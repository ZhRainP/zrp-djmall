package com.dj.p2p.client;

import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.dict.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lindemin
 */
@Slf4j
@Component
public class DictApiFallBack implements DictApi{

    @Override
    public List<Dict> selectDictByParentCode(String parentCode) throws Exception {
        log.info("进入根据父级CODE查询字典数据方法、服务降级");
        return null;
    }

}
