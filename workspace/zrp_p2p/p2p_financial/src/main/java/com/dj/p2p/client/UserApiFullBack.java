package com.dj.p2p.client;

import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserApiFullBack implements UserApi{

    @Override
    public ResultModel findAllBorrower() throws BusinessException {
        log.error("服务降级");
        return new ResultModel().error("查询失败");
    }

    @Override
    public ResultModel findBorrowerByUserId(Integer userId) throws BusinessException {
        log.error("服务降级");
        return new ResultModel().error("查询失败");
    }
}
