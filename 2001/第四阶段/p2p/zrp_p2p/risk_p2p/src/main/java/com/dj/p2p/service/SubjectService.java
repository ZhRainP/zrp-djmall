package com.dj.p2p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.subject.Check;
import com.dj.p2p.pojo.subject.CheckRecord;
import com.dj.p2p.pojo.subject.Subject;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ldm
 * @since 2020-11-30
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 创建标的
     *
     * @param subject 标的信息
     * @return
     * @throws BusinessException
     */
    boolean createSubject(Subject subject) throws BusinessException;

    /**
     * 返回风投控页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws BusinessException
     */
    List<Subject> riskMessage(Integer userId) throws BusinessException;

    /**
     * 初审
     *
     * @param checkRecord 初审信息
     * @param token       用户令牌
     * @return
     * @throws BusinessException
     */
    boolean oneCheck(CheckRecord checkRecord, String token) throws BusinessException;

    /**
     * 返回复审页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws BusinessException
     */
    ResultModel returnRepeatCheckMessage(Integer userId) throws BusinessException;

    /**
     * 复审
     *
     * @param check 复审信息
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    boolean repeatCheck(Check check, String token) throws BusinessException;

    /**
     * 返回理财项目页面信息
     *
     * @return
     * @throws BusinessException
     */
    ResultModel returnFinancialProjectMessage(String token) throws BusinessException;

    /**
     * 返回我要出街页面信息
     *
     * @return
     * @throws BusinessException
     */
    ResultModel returnOutBorrowMessage() throws BusinessException;

    /**
     * 返回购买页面信息
     *
     * @param subjectId 标的ID
     * @return
     * @throws BusinessException
     */
    ResultModel returnBuyMessage(Integer subjectId) throws BusinessException;

    /**
     * 立即购买
     *
     * @param subjectId 标的ID
     * @param price     金额
     * @return
     * @throws BusinessException
     */
    Boolean buySubject(Integer subjectId, BigDecimal price) throws BusinessException;

    /**
     * 返回我的借款页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws BusinessException
     */
    ResultModel returnBorrowMessage(Integer userId) throws BusinessException;

    /**
     * 放款
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @throws BusinessException
     */
    void advancePrice(String token, Integer subjectId) throws Exception;

    /**
     * 签约
     *
     * @param subjectId
     * @throws BusinessException
     */
    void sign(Integer subjectId) throws BusinessException;

    /**
     * 标的完成
     *
     * @param subjectId 标的ID
     * @throws BusinessException
     */
    void overSubject(Integer subjectId) throws BusinessException;

    /**
     * 返回我的出借页面信息
     *
     * @return
     * @throws BusinessException
     */
    ResultModel returnMyOutBorrow(Integer userId) throws BusinessException;

    /**
     * 返回首页页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    ResultModel returnHomePageMessage(String token) throws Exception;
}
