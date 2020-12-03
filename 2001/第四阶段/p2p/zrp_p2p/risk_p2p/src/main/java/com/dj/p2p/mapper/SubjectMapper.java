package com.dj.p2p.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.p2p.pojo.subject.Subject;
import org.springframework.dao.DataAccessException;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.List;


public interface SubjectMapper extends BaseMapper<Subject> {

    /**
     * 返回风控页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws DataAccessException
     */
    List<Subject> riskMessage(Integer userId) throws DataAccessException;

    /**
     * 返回理财项目页面信息
     *
     * @return
     * @throws DataAccessException
     */
    List<Subject> returnFinancialProjectMessage(Integer userId) throws DataAccessException;

    /**
     * 返回我要出借页面信息
     *
     * @return
     * @throws DataAccessException
     */
    List<Subject> returnOutBorrowMessage() throws DataAccessException;

    /**
     * 返回我的借款页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws DataAccessException
     */
    List<Subject> returnBorrowMessage(Integer userId) throws DataAccessException;

    /**
     * 返回我的出借页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws DataAccessException
     */
    List<Subject> returnMyOutBorrow(Integer userId) throws DataAccessException;

    /**
     * 放款项目总额
     *
     * @return
     * @throws DataAccessException
     */
    Subject AllSubjectMoney() throws DataAccessException;
}
