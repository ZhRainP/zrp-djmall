package com.dj.p2p.client;

import com.baomidou.mybatisplus.annotation.TableId;
import com.dj.p2p.common.ResultModel;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author lindemin
 */
@FeignClient(name = "accountManage-service", path = "/manage/", fallback = AccountManageApiFallBack.class)
public interface AccountManageApi {

    /**
     * 新增默认用户关系信息
     * @param token 用户
     * @return
     * @throws Exception
     */
    @PostMapping("addDefaultAccountManage")
    ResultModel addDefaultAccountManage(@RequestHeader("token") String token) throws Exception;

}
