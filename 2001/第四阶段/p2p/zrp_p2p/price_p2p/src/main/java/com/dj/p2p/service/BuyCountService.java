package com.dj.p2p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.pojo.buycount.BuyCount;
import com.dj.p2p.pojo.pricemanage.AccountManage;

import java.util.List;


public interface BuyCountService extends IService<BuyCount> {

    /**
     * 获取投资人投资资金
     *
     * @param subjectId 标的ID
     * @return
     * @throws BusinessException
     */
    List<BuyCount> getInvestorsPrice(Integer subjectId) throws BusinessException;

    /**
     * 获取所有金额
     *
     * @param id 用户ID
     * @return
     * @throws BusinessException
     */
    List<BuyCount> getAllPrice(Integer id) throws BusinessException;

    /**
     * 总收益
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    List<BuyCount> getAllEarningsPrice(Integer id) throws BusinessException;

    /**
     * 根据用户ID与标的ID获取投资资金
     *
     * @param subjectId
     * @param userId
     * @return
     * @throws BusinessException
     */
    BuyCount getInvestorsPriceBySubjectIdAndUserId(Integer subjectId, Integer userId) throws BusinessException;
}
