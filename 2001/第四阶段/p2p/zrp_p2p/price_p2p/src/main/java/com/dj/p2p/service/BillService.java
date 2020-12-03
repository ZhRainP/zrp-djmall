package com.dj.p2p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.financial.Bill;
import com.dj.p2p.pojo.subject.Subject;


public interface BillService extends IService<Bill> {

    /**
     * 返回我要出借页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    ResultModel returnOutBorrowMessage(String token) throws Exception;

    /**
     * 生成账单
     *
     * @param subject 账单信息
     * @throws BusinessException
     */
    void generatedBills(Subject subject) throws BusinessException;

    /**
     * 查看账单
     *
     * @param subjectId 标的ID
     * @return
     * @throws BusinessException
     */
    ResultModel readBills(Integer subjectId) throws BusinessException;

    /**
     * 还款
     *
     * @param billId 账单ID
     * @throws BusinessException
     */
    void rePayBill(String token, Integer billId) throws Exception;

    /**
     * 返回所有待还金额
     *
     * @return
     * @throws BusinessException
     */
    ResultModel returnAllRepayPrice() throws BusinessException;

    /**
     * 待还金额
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    Bill getWaitPrice(Integer id) throws BusinessException;

    /**
     * 投资人查看账单
     *
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    ResultModel investorsReadBills(Integer subjectId,Integer userId) throws Exception;
}
