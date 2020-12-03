package com.dj.p2p.client;

import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.dict.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Component
public class AccountManageApiFallBack implements AccountManageApi {

    /**
     * 新增默认用户关系信息
     *
     * @param token 用户
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel addDefaultAccountManage(String token) throws Exception {
        log.info("进入新增默认用户关系信息方法，服务降级");
        return null;
    }
}
