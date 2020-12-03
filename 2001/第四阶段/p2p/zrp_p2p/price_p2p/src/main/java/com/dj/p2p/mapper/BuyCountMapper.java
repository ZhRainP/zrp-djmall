package com.dj.p2p.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.p2p.pojo.buycount.BuyCount;
import com.dj.p2p.pojo.pricemanage.AccountManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;


public interface BuyCountMapper extends BaseMapper<BuyCount> {

    /**
     * 获取投资人投资资金
     *
     * @param subjectId 标的ID
     * @return
     * @throws DataAccessException
     */
    List<BuyCount> getInvestorsPrice(Integer subjectId) throws DataAccessException;

    /**
     * 获取全部金额
     *
     * @param id 用户ID
     * @return
     */
    List<BuyCount> getAllPrice(Integer id) throws DataAccessException;

    /**
     * 总收益
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    List<BuyCount> getAllEarningsPrice(Integer id) throws DataAccessException;

    /**
     * 投资人查看账单
     * @param subjectId
     * @param userId
     * @return
     * @throws DataAccessException
     */
    BuyCount getInvestorsPriceBySubjectIdAndUserId(@Param("subjectId") Integer subjectId, @Param("userId")Integer userId) throws DataAccessException;
}
