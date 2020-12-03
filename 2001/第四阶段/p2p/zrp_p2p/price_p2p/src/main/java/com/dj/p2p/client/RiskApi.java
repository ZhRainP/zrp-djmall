package com.dj.p2p.client;

import com.dj.p2p.common.ResultModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.POST;
import java.math.BigDecimal;

/**
 * @author lindemin
 */
@FeignClient(name = "risk-service", path = "/subject/", fallback = RiskApiFallBack.class)
public interface RiskApi {

    /**
     * 返回我要出街页面信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping("returnOutBorrowMessage")
    ResultModel returnOutBorrowMessage(@RequestHeader("token") String token) throws Exception;

    /**
     * 返回购买页面信息
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    @PostMapping("returnBuyMessage")
    ResultModel returnBuyMessage(@RequestHeader("token") String token, @RequestParam("subjectId") Integer subjectId) throws Exception;

    /**
     * 立即购买
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @param price     金额
     * @throws Exception
     */
    @PostMapping("buySubject")
    ResultModel buySubject(@RequestHeader("token") String token, @RequestParam("subjectId") Integer subjectId, @RequestParam("price") BigDecimal price) throws Exception;

    /**
     * 返回借款页面信息
     *
     * @param token  用户令牌
     * @param userId 用户ID
     * @return
     * @throws Exception
     */
    @PostMapping("returnBorrowMessage")
    ResultModel returnBorrowMessage(@RequestHeader("token") String token, @RequestParam("userId") Integer userId) throws Exception;

    /**
     * 放款
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @throws Exception
     */
    @PostMapping("sign")
    ResultModel sign(@RequestHeader("token") String token, @RequestParam("subjectId") Integer subjectId) throws Exception;

    /**
     * 订单完成
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    @PostMapping("overSubject")
    ResultModel overSubject(@RequestHeader("token") String token, @RequestParam("subjectId") Integer subjectId) throws Exception;

    /**
     * 返回我的出借页面信息
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    @PostMapping("returnMyOutBorrow")
    ResultModel returnMyOutBorrow(@RequestHeader("token")String token) throws Exception;
}
