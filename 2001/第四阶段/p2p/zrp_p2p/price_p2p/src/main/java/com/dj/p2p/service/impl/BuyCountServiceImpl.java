package com.dj.p2p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.mapper.BuyCountMapper;
import com.dj.p2p.pojo.buycount.BuyCount;
import com.dj.p2p.pojo.pricemanage.AccountManage;
import com.dj.p2p.service.BuyCountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ldm
 * @since 2020-12-02
 */
@Service
public class BuyCountServiceImpl extends ServiceImpl<BuyCountMapper, BuyCount> implements BuyCountService {

    /**
     * 获取投资人投资资金
     *
     * @param subjectId 标的ID
     * @return
     * @throws BusinessException
     */
    @Override
    public List<BuyCount> getInvestorsPrice(Integer subjectId) throws BusinessException {
        return this.baseMapper.getInvestorsPrice(subjectId);
    }

    /**
     * 获取所有金额
     *
     * @param id 用户ID
     * @return
     * @throws BusinessException
     */
    @Override
    public List<BuyCount> getAllPrice(Integer id) throws BusinessException {
        return this.baseMapper.getAllPrice(id);
    }

    /**
     * 总收益
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public List<BuyCount> getAllEarningsPrice(Integer id) throws BusinessException {
        return this.baseMapper.getAllEarningsPrice(id);
    }

    /**
     * 根据用户ID与标的ID获取投资资金
     *
     * @param subjectId
     * @param userId
     * @return
     * @throws BusinessException
     */
    @Override
    public BuyCount getInvestorsPriceBySubjectIdAndUserId(Integer subjectId, Integer userId) throws BusinessException {
        return this.baseMapper.getInvestorsPriceBySubjectIdAndUserId(subjectId,userId);
    }
}
