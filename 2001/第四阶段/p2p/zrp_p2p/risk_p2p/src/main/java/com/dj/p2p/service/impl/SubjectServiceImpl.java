package com.dj.p2p.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.client.PriceApi;
import com.dj.p2p.client.UserApi;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.PasswordSecurityUtil;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.constant.DictConstant;
import com.dj.p2p.constant.UserConstant;
import com.dj.p2p.pojo.subject.Check;
import com.dj.p2p.pojo.subject.CheckRecord;
import com.dj.p2p.pojo.subject.Subject;
import com.dj.p2p.mapper.SubjectMapper;
import com.dj.p2p.pojo.user.User;
import com.dj.p2p.service.CheckRecordService;
import com.dj.p2p.service.CheckService;
import com.dj.p2p.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private CheckRecordService checkRecordService;

    @Autowired
    private CheckService checkService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PriceApi priceApi;

    @Autowired
    private UserApi userApi;

    /**
     * 创建标的
     *
     * @param subject 标的信息
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean createSubject(Subject subject) throws BusinessException {
        subject.setSubjectNo("DJ" + PasswordSecurityUtil.generateRandom(6));
        super.save(subject);
        return true;
    }

    /**
     * 返回风控页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Subject> riskMessage(Integer userId) throws BusinessException {
        List<Subject> subjectList = this.baseMapper.riskMessage(userId);
        subjectList.forEach(subject -> {
            subject.setInterest(subject.getSubjectMoney().multiply(subject.getYearInterest()).setScale(2, BigDecimal.ROUND_HALF_UP));
            subject.setInterestAndPrincipal(subject.getSubjectMoney().add(subject.getInterest()).setScale(2, BigDecimal.ROUND_HALF_UP));
        });
        return subjectList;
    }

    /**
     * 初审
     *
     * @param checkRecord 初审信息
     * @param token       用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean oneCheck(CheckRecord checkRecord, String token) throws BusinessException {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(UserConstant.RISK_CONTROL_MANAGER_CODE)) {
            throw new BusinessException(-1, "级别不足");
        }
        Subject subject = super.getById(checkRecord.getSubjectId());
        if (!subject.getProAuditStatus().equals(DictConstant.ONE_CHECK_CODE)) {
            throw new BusinessException(-1, "已经初审过了");
        }
        checkRecord.setCheckUserId(user.getId());
        checkRecord.setCheckType(DictConstant.ONE_AUDIT_CODE);
        checkRecordService.save(checkRecord);
        UpdateWrapper<Subject> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", checkRecord.getSubjectId());
        if (checkRecord.getAuditStatus().equals(DictConstant.AUDIT_STATUS_NO_PASS_CODE)) {
            updateWrapper.set("pro_audit_status", DictConstant.CHECK_FAILURE_CODE);
        } else {
            updateWrapper.set("pro_audit_status", DictConstant.REPEAT_CHECK_CODE);
        }
        super.update(updateWrapper);
        return true;
    }

    /**
     * 返回复审页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnRepeatCheckMessage(Integer userId) throws BusinessException {
        List<Subject> subject = riskMessage(userId);
        QueryWrapper<CheckRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("check_user_id", userId);
        List<CheckRecord> checkRecordList = checkRecordService.list(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("subject", subject);
        map.put("checkRecord", checkRecordList);
        return new ResultModel().success(map);
    }

    /**
     * 复审
     *
     * @param check 复审信息
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean repeatCheck(Check check, String token) throws BusinessException {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(UserConstant.DIRECTOR_RISK_CONTROL_CODE)) {
            throw new BusinessException(-1, "级别不足");
        }
        Subject subject = super.getById(check.getSubjectId());
        if (!subject.getProAuditStatus().equals(DictConstant.REPEAT_CHECK_CODE)) {
            throw new BusinessException(-1, "已经复审过了");
        }
        if (!check.getAuditStatus().equals(DictConstant.AUDIT_STATUS_NO_PASS_CODE)) {
            checkService.save(check);
        }
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setCheckUserId(user.getId());
        checkRecord.setSubjectId(check.getSubjectId());
        checkRecord.setCheckOpinion(check.getCheckOpinion());
        checkRecord.setCheckType(check.getCheckType());
        checkRecord.setAuditStatus(check.getAuditStatus());
        checkRecord.setCheckType(DictConstant.REPEAT_AUDIT_CODE);
        checkRecordService.save(checkRecord);
        UpdateWrapper<Subject> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", checkRecord.getSubjectId());
        if (checkRecord.getAuditStatus().equals(DictConstant.AUDIT_STATUS_NO_PASS_CODE)) {
            updateWrapper.set("pro_audit_status", DictConstant.REPEAT_AUDIT_FAILURE_CODE);
        } else {
            updateWrapper.set("pro_audit_status", DictConstant.OVER_CHECK_CODE);
        }
        super.update(updateWrapper);
        return true;
    }

    /**
     * 返回理财项目页面信息
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnFinancialProjectMessage(String token) throws BusinessException {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        Integer userId = null;
        if (user.getLevel().equals(DictConstant.BORROWER_CODE) || user.getLevel().equals(DictConstant.INVESTORS_CODE)) {
            userId = user.getId();
        }
        List<Subject> subjectList = this.baseMapper.returnFinancialProjectMessage(userId);
        subjectList.forEach(subject -> {
            subject.setInterest(subject.getSubjectMoney().multiply(subject.getYearInterest()).setScale(2, BigDecimal.ROUND_HALF_UP));
            subject.setInterestAndPrincipal(subject.getSubjectMoney().add(subject.getInterest()).setScale(2, BigDecimal.ROUND_HALF_UP));
            subject.setRaiseSchedule(subject.getRaiseMoney().divide(subject.getSubjectMoney(), 2, RoundingMode.HALF_UP));
        });
        return new ResultModel().success(subjectList);
    }

    /**
     * 返回我要出街页面信息
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnOutBorrowMessage() throws BusinessException {
        List<Subject> subjectList = this.baseMapper.returnOutBorrowMessage();
        Map<String, Object> map = new HashMap<>();
        map.put("subjectList", subjectList);
        return new ResultModel().success(map);
    }

    /**
     * 返回购买页面信息
     *
     * @param subjectId 标的ID
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnBuyMessage(Integer subjectId) throws BusinessException {
        Subject subject = super.getById(subjectId);
        Map<String, Object> map = new HashMap<>();
        if (subject.getIsLimit().equals(DictConstant.SIGLE_CODE)) {
            map.put("maxBuyPrice", subject.getSubjectMoney().multiply(new BigDecimal("60.00")).multiply(new BigDecimal("0.01")));
        } else {
            map.put("maxBuyPrice", subject.getSubjectMoney().subtract(subject.getRaiseMoney()));
        }
        map.put("progress", subject.getRaiseMoney().divide(subject.getSubjectMoney()).multiply(new BigDecimal("100.00")).setScale(2, BigDecimal.ROUND_HALF_UP));
        return new ResultModel().success(map);
    }

    /**
     * 立即购买
     *
     * @param subjectId 标的ID
     * @param price     金额
     * @return
     * @throws BusinessException
     */
    @Override
    public Boolean buySubject(Integer subjectId, BigDecimal price) throws BusinessException {
        Subject subject = super.getById(subjectId);
        UpdateWrapper<Subject> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("raise_money", subject.getRaiseMoney().add(price));
        updateWrapper.set("subject_join_num", subject.getSubjectJoinNum() + 1);
        if (subject.getSubjectMoney().compareTo(subject.getRaiseMoney().add(price)) == 0) {
            updateWrapper.set("pro_status", DictConstant.WAIT_SIGN_CODE);
        }
        updateWrapper.eq("id", subjectId);
        super.update(updateWrapper);
        return true;
    }

    /**
     * 返回我的借款页面信息
     *
     * @param userId 用户ID
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnBorrowMessage(Integer userId) throws BusinessException {
        List<Subject> subjectList = this.baseMapper.returnBorrowMessage(userId);
        subjectList.forEach(subject -> {
            subject.setRaiseSchedule(subject.getRaiseMoney().divide(subject.getSubjectMoney(), 2, RoundingMode.HALF_UP));
        });
        return new ResultModel().success(subjectList);
    }

    /**
     * 签约
     *
     * @param subjectId
     * @throws BusinessException
     */
    @Override
    public void sign(Integer subjectId) throws BusinessException {
        Subject subject = super.getById(subjectId);
        if(!subject.getProStatus().equals(DictConstant.WAIT_SIGN_CODE)){
            throw new BusinessException(-1,"不是待签约状态");
        }
        UpdateWrapper<Subject> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pro_status", DictConstant.WAIT_STATUS_CODE);
        updateWrapper.eq("id", subjectId);
        super.update(updateWrapper);
    }

    /**
     * 放款
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @throws BusinessException
     */
    @Override
    public void advancePrice(String token, Integer subjectId) throws Exception {
        Subject subject = super.getById(subjectId);
        if (!subject.getProStatus().equals(DictConstant.WAIT_STATUS_CODE)) {
            throw new BusinessException(-1, "还未是待放款状态");
        }
        UpdateWrapper<Subject> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pro_status", DictConstant.REPAY_STATUS_CODE);
        updateWrapper.eq("id", subjectId);
        super.update(updateWrapper);
        //清空投资人冻结资金存入借款人可用资金
        priceApi.saveBorrowerPrice(token, subject.getBorrowerId(), subjectId);
        //生成账单
        subject.setProStartTime(null);
        subject.setProUpdateTime(null);
        priceApi.generatedBills(token, subject);
    }

    /**
     * 标的完成
     *
     * @param subjectId 标的ID
     * @throws BusinessException
     */
    @Override
    public void overSubject(Integer subjectId) throws BusinessException {
        UpdateWrapper<Subject> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pro_status", DictConstant.OVER_STATUS_CODE);
        updateWrapper.eq("id", subjectId);
        super.update(updateWrapper);
    }

    /**
     * 返回我的出借页面信息
     *
     * @param userId
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnMyOutBorrow(Integer userId) throws BusinessException {
        List<Subject> subjectList = this.baseMapper.returnMyOutBorrow(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("subjectList", subjectList);
        return new ResultModel().success(map);
    }

    /**
     * 返回首页页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel returnHomePageMessage(String token) throws Exception {
        Subject allPrice = this.baseMapper.AllSubjectMoney();
        Map<String, Object> userMap = (Map<String, Object>) userApi.returnHomePageMessage(token).getData();
        Map<String, Object> priceMap = (Map<String, Object>) priceApi.returnAllRePayPrice(token).getData();
        Map<String, Object> map = new HashMap<>();
        map.put("registerNumber",userMap.get("registerNumber"));
        map.put("allSubjectPrice",allPrice.getAllPrice());
        map.put("allRepayPrice",priceMap.get("allRepayPrice"));
        return new ResultModel().success(map);
    }
}
