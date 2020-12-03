package com.dj.p2p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.pricemanage.AccountManage;

import java.math.BigDecimal;


public interface AccountManageService extends IService<AccountManage> {

    /**
     * 计算金额
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    ResultModel countPrice(String token) throws Exception;

    /**
     * 返回可用余额
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    BigDecimal getAvailablePrice(String token) throws BusinessException;

    /**
     * 充值金额
     *
     * @param token         用户令牌
     * @param rechargePrice 充值金额
     * @return
     * @throws BusinessException
     */
    boolean rechargePrice(String token, BigDecimal rechargePrice) throws BusinessException;

    /**
     * 新增默认用户管理信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    boolean addDefaultAccountManage(String token) throws BusinessException;

    /**
     * 提现金额
     *
     * @param token         用户令牌
     * @param withdrawPrice 提现金额
     * @return
     * @throws BusinessException
     */
    boolean userWithdraw(String token, BigDecimal withdrawPrice) throws BusinessException;

    /**
     * 返回购买页面信息
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    ResultModel returnBuyMessage(String token, Integer subjectId) throws Exception;

    /**
     * 立即购买
     *
     * @param token         用户令牌
     * @param accountManage 购买信息
     * @return
     * @throws Exception
     */
    Boolean buySubject(String token, AccountManage accountManage) throws Exception;

    /**
     * 返回我的借款页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    ResultModel returnBorrowMessage(String token) throws Exception;

    /**
     * 签约
     *
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    ResultModel sign(String token, Integer subjectId) throws Exception;

    /**
     * 资金进入借款人账户
     *
     * @param token     用户令牌
     * @param userId    用户ID
     * @param subjectId 标的ID
     * @return
     * @throws BusinessException
     */
    ResultModel saveBorrowerPrice(String token, Integer userId, Integer subjectId) throws BusinessException;

    /**
     * 返回我的出借页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    ResultModel returnMyOutBorrow(String token) throws Exception;

}
