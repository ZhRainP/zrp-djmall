package com.dj.p2p.client;

import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.dict.Dictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DictApiFullBack implements DictApi{
    @Override
    public ResultModel toBid() throws BusinessException {
        log.error("服务降级");
        return new ResultModel().error("查询失败");
    }

    @Override
    public ResultModel toFindContract() throws BusinessException {
        log.error("服务降级");
        return new ResultModel().error("查询失败");
    }

}
