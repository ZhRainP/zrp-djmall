package com.dj.p2p.client;

import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.dict.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Slf4j
@Component
public class RiskApiFallBack implements RiskApi {


    /**
     * 返回我要出借页面信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel returnOutBorrowMessage(String token) throws Exception {
        log.info("返回我要出借页面信息方法，服务降级");
        return null;
    }

    @Override
    public ResultModel returnBuyMessage(String token, Integer subjectId) throws Exception {
        log.info("返回购买页面信息方法，服务降级");
        return null;
    }

    /**
     * 立即购买
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @param price     金额
     * @throws Exception
     */
    @Override
    public ResultModel buySubject(String token, Integer subjectId, BigDecimal price) throws Exception {
        log.info("立即购买方法，服务降级");
        return null;
    }

    /**
     * 返回借款页面信息
     *
     * @param token  用户令牌
     * @param userId 用户ID
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel returnBorrowMessage(String token, Integer userId) throws Exception {
        log.info("返回借款页面信息方法，服务降级");
        return null;
    }

    /**
     * 放款
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @throws Exception
     */
    @Override
    public ResultModel sign(String token, Integer subjectId) throws Exception {
        log.info("放款方法，服务降级");
        return null;
    }

    /**
     * 订单完成
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel overSubject(String token, Integer subjectId) throws Exception {
        log.info("订单完成方法，服务降级");
        return null;
    }

    /**
     * 返回我的出借页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel returnMyOutBorrow(String token) throws Exception {
        log.info("返回我的出借页面信息方法，服务降级");
        return null;
    }
}
