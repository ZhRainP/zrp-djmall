package com.dj.p2p.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.p2p.pojo.financial.Bill;
import org.springframework.dao.DataAccessException;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ldm
 * @since 2020-12-02
 */
public interface BillMapper extends BaseMapper<Bill> {

    /**
     * 返回所有待还金额
     *
     * @return
     * @throws DataAccessException
     */
    Bill returnAllRepayPrice() throws DataAccessException;

    /**
     * 待还金额
     * @param id
     * @return
     * @throws DataAccessException
     */
    Bill getWaitPrice(Integer id) throws DataAccessException;
}
