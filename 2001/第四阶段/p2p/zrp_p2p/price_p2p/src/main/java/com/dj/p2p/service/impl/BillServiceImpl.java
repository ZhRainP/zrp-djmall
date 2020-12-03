package com.dj.p2p.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.client.RiskApi;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.PasswordSecurityUtil;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.constant.DictConstant;
import com.dj.p2p.mapper.BillMapper;
import com.dj.p2p.pojo.buycount.BuyCount;
import com.dj.p2p.pojo.financial.Bill;
import com.dj.p2p.pojo.pricemanage.AccountManage;
import com.dj.p2p.pojo.subject.Subject;
import com.dj.p2p.pojo.user.User;
import com.dj.p2p.service.AccountManageService;
import com.dj.p2p.service.BillService;
import com.dj.p2p.service.BuyCountService;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    @Autowired
    private RiskApi riskApi;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BuyCountService buyCountService;

    @Autowired
    private AccountManageService accountManageService;


    /**
     * 返回我要出借页面信息
     *
     * @param token
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnOutBorrowMessage(String token) throws Exception {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE)) {
            throw new BusinessException(-4, "只有投资人才可访问，您无权限！");
        }
        ResultModel resultModel = riskApi.returnOutBorrowMessage(token);
        return resultModel;
    }

    /**
     * 生成账单
     *
     * @param subject 账单信息
     * @throws BusinessException
     */
    @Override
    public void generatedBills(Subject subject) throws BusinessException {
        List<Bill> list = new ArrayList<>();
        for (int i = 1; i < subject.getTimeLimit() + 1; i++) {
            Bill bill = new Bill();
            //标的ID
            bill.setSubjectId(subject.getId());
            //编号
            bill.setBillNo("DJ" + PasswordSecurityUtil.generateRandom(5));
            //本金    总金额除期数
            bill.setPrincipal(subject.getSubjectMoney().divide(new BigDecimal(subject.getTimeLimit()), 2, BigDecimal.ROUND_HALF_UP)
                    .setScale(2, BigDecimal.ROUND_HALF_UP));
            //利息    总金额乘利率  除期数
            bill.setInterest(subject.getSubjectMoney().multiply(subject.getYearInterest()).multiply(new BigDecimal("0.01"))
                    .divide(new BigDecimal(subject.getTimeLimit()), 2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP));
            //本息
            bill.setPrincipalAndInterest(bill.getPrincipal().add(bill.getInterest()).setScale(2, BigDecimal.ROUND_HALF_UP));
            //时间
            bill.setOverTime(LocalDate.now().plusMonths(i + 1));
            //期数
            bill.setTimeNumber(i + "/" + subject.getTimeLimit().toString());
            //状态
            bill.setBillStatus(DictConstant.NO_REPAY_CODE);
            list.add(bill);
        }
        super.saveBatch(list);
    }

    /**
     * 查看账单
     *
     * @param subjectId 标的ID
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel readBills(Integer subjectId) throws BusinessException {
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id", subjectId);
        List<Bill> billList = super.list(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("billList", billList);
        return new ResultModel().success(map);
    }

    /**
     * 还款
     *
     * @param billId 账单ID
     * @throws BusinessException
     */
    @Override
    public void rePayBill(String token, Integer billId) throws Exception {
        //还款
        UpdateWrapper<Bill> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("bill_status", DictConstant.YES_REPAY_CODE);
        updateWrapper.eq("id", billId);
        updateWrapper.set("back_time", LocalDateTime.now());
        super.update(updateWrapper);

        //判断是否还有未还的
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", billId);
        Bill bill = super.getOne(queryWrapper);
        QueryWrapper<Bill> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("bill_status", DictConstant.NO_REPAY_CODE);
        queryWrapper1.eq("subject_id", bill.getSubjectId());
//        //借款还给投资人
//        List<BuyCount> priceList = buyCountService.getInvestorsPrice(bill.getSubjectId());
//        //算出总金额
//        BigDecimal countPrice = priceList.stream().map(BuyCount::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
//        //算出百分比
//        List<AccountManage> accountManageList = new ArrayList<>();
//        for (BuyCount buyCount : priceList) {
//            QueryWrapper<AccountManage> queryWrapper2 = new QueryWrapper<>();
//            queryWrapper2.eq("user_id", buyCount.getBuyerId());
//            AccountManage accountManage = accountManageService.getOne(queryWrapper2);
//            //算百分比
//            BigDecimal percentage = new BigDecimal(String.valueOf(buyCount.getPrice().divide(countPrice, 2, BigDecimal.ROUND_HALF_UP)));
//            //每一期分到的钱  当前可用余额+分到的钱
//            accountManage.setAvailablePrice(accountManage.getAvailablePrice().add(bill.getPrincipalAndInterest()).multiply(percentage));
//            //放到list中
//            accountManageList.add(accountManage);
//        }
//        //批量修改
//        accountManageService.updateBatchById(accountManageList);
        //最后一期还完变成已完成状态
        List<Bill> billList = super.list(queryWrapper);
        if (billList.size() == 1) {
            riskApi.overSubject(token, bill.getSubjectId());
            List<BuyCount> priceList = buyCountService.getInvestorsPrice(bill.getSubjectId());
            List<Bill> billList1 = super.list();
//            //利息加本金
            BigDecimal allPrice = billList1.stream().map(Bill::getPrincipalAndInterest).reduce(BigDecimal.ZERO, BigDecimal::add);
//            //本金
            BigDecimal price = billList1.stream().map(Bill::getPrincipal).reduce(BigDecimal.ZERO, BigDecimal::add);
            List<AccountManage> list = new ArrayList<>();
            priceList.forEach(p -> {
                QueryWrapper<AccountManage> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("user_id", p.getBuyerId());
                AccountManage manage = accountManageService.getOne(queryWrapper2);
                BigDecimal avaPrice = p.getPrice().divide(price, 2, BigDecimal.ROUND_HALF_UP);
                avaPrice = avaPrice.multiply(allPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                manage.setAvailablePrice(manage.getAvailablePrice().add(avaPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
                list.add(manage);
            });
            accountManageService.updateBatchById(list);
        }
    }

    /**
     * 返回所有待还金额
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnAllRepayPrice() throws BusinessException {
        Bill bill = this.baseMapper.returnAllRepayPrice();
        Map<String, Object> map = new HashMap<>();
        map.put("allRepayPrice", bill.getAllRepayPrice());
        return new ResultModel().success(map);
    }

    /**
     * 待还金额
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public Bill getWaitPrice(Integer id) throws BusinessException {
        return this.baseMapper.getWaitPrice(id);
    }

    /**
     * 投资人查看账单
     *
     * @param subjectId 标的ID
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel investorsReadBills(Integer subjectId, Integer userId) throws Exception {
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subject_id", subjectId);
        List<Bill> billList = super.list(queryWrapper);
        BuyCount price = buyCountService.getInvestorsPriceBySubjectIdAndUserId(subjectId, userId);
        BigDecimal percentage = price.getPrice().divide(price.getSubjectMoney(),2,BigDecimal.ROUND_HALF_UP);
        Map<String, Object> map = new HashMap<>();
        for (Bill bill : billList) {
            bill.setPrincipalAndInterest(bill.getPrincipalAndInterest().multiply(percentage).setScale(2,BigDecimal.ROUND_HALF_UP));
            bill.setPrincipal(bill.getPrincipal().multiply(percentage).setScale(2,BigDecimal.ROUND_HALF_UP));
            bill.setInterest(bill.getInterest().multiply(percentage).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        map.put("billList", billList);
        return new ResultModel().success(map);
    }
}
