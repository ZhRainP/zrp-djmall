package com.dj.p2p.client;

import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.subject.Subject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.transform.Result;

/**
 * @author lindemin
 */
@FeignClient(name = "accountManage-service", path = "/manage/")
public interface PriceApi {

    /**
     * 存入借款人账户
     *
     * @param token     用户令牌
     * @param userId    用户ID
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    @PostMapping("saveBorrowerPrice")
    ResultModel saveBorrowerPrice(@RequestHeader("token") String token, @RequestParam("userId") Integer userId, @RequestParam("subjectId") Integer subjectId) throws Exception;

    /**
     * 生成账单
     *
     * @param token   用户TOKEN
     * @param subject 账单信息
     * @return
     * @throws Exception
     */
    @PostMapping("generatedBills")
    ResultModel generatedBills(@RequestHeader("token") String token, Subject subject) throws Exception;

    @PostMapping("returnAllRePayPrice")
    ResultModel returnAllRePayPrice(@RequestHeader("token")String token) throws Exception;
}
