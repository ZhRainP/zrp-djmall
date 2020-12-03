package com.dj.p2p.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.p2p.client.RiskApi;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.constant.DictConstant;
import com.dj.p2p.constant.UserConstant;
import com.dj.p2p.mapper.AccountManageMapper;
import com.dj.p2p.pojo.buycount.BuyCount;
import com.dj.p2p.pojo.financial.Bill;
import com.dj.p2p.pojo.openaccount.OpenAccount;
import com.dj.p2p.pojo.pricemanage.AccountManage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.pojo.user.User;
import com.dj.p2p.service.AccountManageService;
import com.dj.p2p.service.BillService;
import com.dj.p2p.service.BuyCountService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class AccountManageServiceImpl extends ServiceImpl<AccountManageMapper, AccountManage> implements AccountManageService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RiskApi riskApi;

    @Autowired
    private BuyCountService buyCountService;

    @Autowired
    private BillService billService;


    public User returnRedisUser(String token) throws BusinessException {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        return user;
    }

    /**
     * 计算金额
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel countPrice(String token) throws BusinessException {
        User user = returnRedisUser(token);
        QueryWrapper<AccountManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        AccountManage manage = super.getOne(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        if (user.getLevel().equals(UserConstant.INVESTORS_CODE)) {
            //投资人
            AccountManage investors = new AccountManage();
            //可用余额
            investors.setAvailablePrice(manage.getAvailablePrice().setScale(2, BigDecimal.ROUND_HALF_UP));
            //总资产 可用金额+冻结金额
            investors.setAllPrice(manage.getAmountPrice().add(manage.getAvailablePrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            //冻结金额
            investors.setAmountPrice(manage.getAmountPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
            //代收金额 还未计算  投资出去的钱
            //  代收
            List<BuyCount> waitPriceList = buyCountService.getAllPrice(user.getId());
            //总代收金额
            BigDecimal waitPrice = new BigDecimal("0.00");
            for (BuyCount count : waitPriceList) {
                //利息加本金总额
                count.setInterestAndSubjectMoney(count.getSubjectMoney().add(count.getSubjectMoney().multiply(count.getYearInterest())
                        .multiply(new BigDecimal("0.01").setScale(2,BigDecimal.ROUND_HALF_UP))));
                //代收金额
                waitPrice = waitPrice.add(count.getPrice().divide(count.getSubjectMoney(),2,BigDecimal.ROUND_HALF_UP)
                        .multiply(count.getInterestAndSubjectMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
            //总代收金额
            investors.setWaitPrice(waitPrice);
            //收益
            List<BuyCount> earningsList = buyCountService.getAllEarningsPrice(user.getId());
            BigDecimal  earnings = new BigDecimal("0.00");
            for (BuyCount count : earningsList) {
                //利息
                count.setInterest(count.getSubjectMoney().multiply(count.getYearInterest())
                        .multiply(new BigDecimal("0.01")).setScale(2,BigDecimal.ROUND_HALF_UP));
                //收益
                earnings = earnings.add(count.getPrice().divide(count.getSubjectMoney(),2,BigDecimal.ROUND_HALF_UP)
                        .multiply(count.getInterest()).setScale(2,BigDecimal.ROUND_HALF_UP));
            }
            //总收益 还未计算  投资出去的钱的利息
            investors.setAllRevenue(earnings);
            //代还金额 投资人无待还金额
            investors.setBackPrice(new BigDecimal(0));
            return new ResultModel().success(investors);
        } else {
            //借款人
            AccountManage borrower = new AccountManage();
            //可用余额  借款金额没有提现的
            borrower.setAvailablePrice(manage.getAvailablePrice());
            //总收益 无
            borrower.setAllRevenue(new BigDecimal(0));
            //冻结金额
            borrower.setAmountPrice(manage.getAmountPrice());
            //代收金额 无
            borrower.setWaitPrice(new BigDecimal(0));
            //待还金额计算
            Bill bill = billService.getWaitPrice(user.getId());
            if(bill==null){
                borrower.setBackPrice(new BigDecimal("0.00"));
            }else{
                //代还金额
                borrower.setBackPrice(bill.getAllRepayPrice());
            }

            //总资产 待还金额+可用余额（如果可用余额是0的话，那么为负的待还金额
            if (manage.getAvailablePrice().compareTo(new BigDecimal(0)) == -1 || manage.getAvailablePrice().compareTo(new BigDecimal(0)) == 0) {
                borrower.setAllPrice(manage.getWaitPrice().multiply(new BigDecimal("-1")));
            } else {
                borrower.setAllPrice(manage.getAvailablePrice().add(borrower.getWaitPrice()));
            }
            return new ResultModel().success(borrower);
        }
    }

    /**
     * 返回可用余额
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public BigDecimal getAvailablePrice(String token) throws BusinessException {
        User user = returnRedisUser(token);
        QueryWrapper<AccountManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        AccountManage manage = super.getOne(queryWrapper);
        return manage.getAvailablePrice();
    }

    /**
     * 充值金额
     *
     * @param token         用户令牌
     * @param rechargePrice 充值金额
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean rechargePrice(String token, BigDecimal rechargePrice) throws BusinessException {
        User user = returnRedisUser(token);
        QueryWrapper<AccountManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        AccountManage manage = super.getOne(queryWrapper);
        UpdateWrapper<AccountManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("available_price", manage.getAvailablePrice().add(rechargePrice));
        updateWrapper.eq("id", manage.getId());
        super.update(updateWrapper);
        return true;
    }

    /**
     * 新增默认用户管理信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean addDefaultAccountManage(String token) throws BusinessException {
        User user = returnRedisUser(token);
        AccountManage accountManage = new AccountManage();
        accountManage.setUserId(user.getId());
        accountManage.setAmountPrice(new BigDecimal("0.00"));
        accountManage.setAvailablePrice(new BigDecimal("0.00"));
        super.save(accountManage);
        return true;
    }

    /**
     * 提现金额
     *
     * @param token         用户令牌
     * @param withdrawPrice 提现金额
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean userWithdraw(String token, BigDecimal withdrawPrice) throws BusinessException {
        User user = returnRedisUser(token);
        QueryWrapper<AccountManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        AccountManage manage = super.getOne(queryWrapper);
        if (manage.getAvailablePrice().compareTo(withdrawPrice) == -1) {
            throw new BusinessException(-1, "提现金额超过可用金额！");
        }
        UpdateWrapper<AccountManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("available_price", manage.getAvailablePrice().subtract(withdrawPrice));
        updateWrapper.eq("id", manage.getId());
        super.update(updateWrapper);
        return true;
    }

    /**
     * 返回购买页面信息
     *
     * @param token     用户令牌
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel returnBuyMessage(String token, Integer subjectId) throws Exception {
        User user = returnRedisUser(token);
        QueryWrapper<AccountManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        AccountManage manage = super.getOne(queryWrapper);
        Map<String, Object> map = (Map<String, Object>) riskApi.returnBuyMessage(token, subjectId).getData();
        map.put("availablePrice", manage.getAvailablePrice());
        map.put("subjectId", subjectId);
        return new ResultModel().success(map);
    }

    /**
     * 立即购买
     *
     * @param token         用户令牌
     * @param accountManage 购买信息
     * @throws Exception
     */
    @Override
    public Boolean buySubject(String token, AccountManage accountManage) throws Exception {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE)) {
            throw new BusinessException(-4, "只有投资人才可访问，您无权限！");
        }
        QueryWrapper<AccountManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        AccountManage manage = super.getOne(queryWrapper);
        if (manage.getAvailablePrice().compareTo(accountManage.getPrice()) == -1) {
            throw new BusinessException(-1, "账户余额不足");
        }
        if (accountManage.getMaxBuyPrice().compareTo(accountManage.getPrice()) == -1) {
            throw new BusinessException(-1, "购买大于最大可投金额");
        }
        UpdateWrapper<AccountManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("available_price", manage.getAvailablePrice().subtract(accountManage.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        updateWrapper.set("amount_price", manage.getAmountPrice().add(accountManage.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        updateWrapper.eq("user_id", user.getId());
        super.update(updateWrapper);
        riskApi.buySubject(token, accountManage.getSubjectId(), accountManage.getPrice());
        BuyCount buyCount = new BuyCount();
        buyCount.setBuyerId(user.getId());
        buyCount.setPrice(accountManage.getPrice());
        buyCount.setSubjectId(accountManage.getSubjectId());
        buyCountService.save(buyCount);
        return true;
    }

    /**
     * 返回我的借款页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel returnBorrowMessage(String token) throws Exception {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.BORROWER_CODE)) {
            throw new BusinessException(-4, "只有借款人才可访问，您无权限！");
        }
        return riskApi.returnBorrowMessage(token, user.getId());
    }

    /**
     * 签约
     *
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel sign(String token,Integer subjectId) throws Exception {
        return riskApi.sign(token,subjectId);
    }

    /**
     * 资金进入借款人账户
     *
     * @param token     用户令牌
     * @param userId    用户ID
     * @param subjectId 标的ID
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel saveBorrowerPrice(String token, Integer userId, Integer subjectId) throws BusinessException {
        //所有需要扣钱的投资人账户  先得到所有投了该账户的投资人 然后去得到这些投资人的账户  再去扣他们的冻结资金   再将资金存入借款人账户
        List<BuyCount> list = buyCountService.getInvestorsPrice(subjectId);
        List<AccountManage> accountManageList = new ArrayList<>();
        BigDecimal bigDecimal = new BigDecimal("0.00");
        for (BuyCount count : list) {
            bigDecimal=(bigDecimal.add(count.getPrice()));
            QueryWrapper<AccountManage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",count.getBuyerId());
            AccountManage accountManage = super.getOne(queryWrapper);
            accountManage.setAmountPrice(accountManage.getAmountPrice().subtract(count.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            accountManageList.add(accountManage);
        }
        super.updateBatchById(accountManageList);
        UpdateWrapper<AccountManage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("available_price",bigDecimal);
        updateWrapper.eq("user_id",userId);
        super.update(updateWrapper);
        return new ResultModel().success();
    }

    /**
     * 返回我的出借页面信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnMyOutBorrow(String token) throws Exception {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE)) {
            throw new BusinessException(-4, "只有投资人才可访问，您无权限！");
        }
        return riskApi.returnMyOutBorrow(token);
    }
}
