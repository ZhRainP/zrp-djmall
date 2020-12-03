package com.dj.p2p.controller;


import com.dj.p2p.client.DictApi;
import com.dj.p2p.client.RiskApi;
import com.dj.p2p.client.UserApi;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.constant.DictConstant;
import com.dj.p2p.pojo.dict.Dict;
import com.dj.p2p.pojo.pricemanage.AccountManage;
import com.dj.p2p.pojo.subject.Subject;
import com.dj.p2p.pojo.user.User;
import com.dj.p2p.service.AccountManageService;
import com.dj.p2p.service.BillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.Cache;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ldm
 * @since 2020-11-28
 */
@RestController
@RequestMapping("/manage/")
@Api(tags = "账户管理API")
@Slf4j
public class AccountManageController {

    @Autowired
    private AccountManageService accountManageService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserApi userApi;

    @Autowired
    private DictApi dictApi;

    @Autowired
    private BillService billService;

    @PostMapping("say")
    public String say() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("1", 1, 24 * 3600 * 1000, TimeUnit.SECONDS);
        return "3333";
    }

    @PostMapping("countPrice")
    @ApiOperation(value = "账户管理计算金额")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel countPrice(@RequestHeader("token") String token) throws Exception {
        log.info("进入账户管理计算金额方法");
        ResultModel resultModel = accountManageService.countPrice(token);
        log.info("结束账户管理计算金额方法");
        return resultModel;
    }

//    @PostMapping("returnAvailablePrice")
//    @ApiOperation("返回可用余额")
//    @ApiImplicitParam(name = "token", value = "用户令牌")
//    public ResultModel returnAvailablePrice(@RequestHeader("token") String token) throws Exception {
//        log.info("进入返回可用余额方法");
//        ResultModel resultModel = accountManageService.countPrice(token);
//        log.info("结束返回可用余额方法");
//        return resultModel;
//    }

    @PostMapping("returnRechargeMessage")
    @ApiOperation(value = "返回充值页面所需信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnRechargeMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回充值页面所需信息方法");
        ResultModel resultModel = userApi.securityCenter(token);
        log.info("结束返回充值页面所需信息方法");
        return resultModel;
    }

    @PostMapping("rechargePrice")
    @ApiOperation(value = "用户充值")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel rechargePrice(@RequestHeader("token") String token, @RequestBody AccountManage accountManage) throws Exception {
        log.info("进入用户充值方法，金额为：{},支付密码为：", accountManage.getPrice(), accountManage.getPayPassword());
        Assert.state(accountManage.getPrice().compareTo(new BigDecimal("10.00")) != -1, "最小金额要大于10元");
        Assert.notNull(accountManage.getPrice(), "充值金额不能为空");
        Assert.hasText(accountManage.getPayPassword(), "支付密码不能为空");
        String returnPayPassword = userApi.returnPayPassword(token);
        Assert.state(accountManage.getPayPassword().equals(returnPayPassword), "两次输入支付密码不一致");
        accountManageService.rechargePrice(token, accountManage.getPrice());
        log.info("结束用户充值方法");
        return new ResultModel().success();
    }


    @PostMapping("returnWithdrawMessage")
    @ApiOperation(value = "返回提现页面所需信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnWithdrawMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回提现页面所需信息方法");
        BigDecimal availablePrice = accountManageService.getAvailablePrice(token);
        String bankNumber = userApi.returnBankNumber(token);
        List<Dict> withdrawList = dictApi.selectDictByParentCode(DictConstant.WITHDRAW_TYPE_CODE);
        Map<String, Object> map = new HashMap<>();
        map.put("availablePrice", availablePrice);
        map.put("idCard", bankNumber);
        map.put("withdrawList", withdrawList);
        log.info("结束返回提现页面所需信息方法");
        return new ResultModel().success(map);
    }

    @PostMapping("addDefaultAccountManage")
    @ApiOperation(value = "新增默认用户管理信息（user调用）")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel addDefaultAccountManage(@RequestHeader("token") String token) throws Exception {
        log.info("进入新增默认用户管理信息方法");
        accountManageService.addDefaultAccountManage(token);
        log.info("结束新增默认用户管理信息方法");
        return new ResultModel().success();
    }

    @PostMapping("withdraw")
    @ApiOperation(value = "用户提现")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel withdraw(@RequestHeader("token") String token, @RequestBody AccountManage accountManage) throws Exception {
        log.info("进入用户提现方法，金额为：{},支付密码为：", accountManage.getPrice(), accountManage.getPayPassword());
        Assert.notNull(accountManage.getPrice(), "充值金额不能为空");
        Assert.hasText(accountManage.getPayPassword(), "支付密码不能为空");
        String returnPayPassword = userApi.returnPayPassword(token);
        Assert.state(accountManage.getPayPassword().equals(returnPayPassword), "两次输入支付密码不一致");
        accountManageService.userWithdraw(token, accountManage.getPrice());
        log.info("结束用户提现方法");
        return new ResultModel().success();
    }

    @PostMapping("returnOutBorrowMessage")
    @ApiOperation("返回我要出借页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnOutBorrowMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回我要出借页面信息方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE) || StringUtils.isEmpty(user.getLevel())) {
            return new ResultModel().error(-5, "必须是投资人才能操作");
        }
        ResultModel resultModel = billService.returnOutBorrowMessage(token);
        log.info("结束返回我要出借页面信息方法");
        return resultModel;
    }

    @PostMapping("returnBuyMessage")
    @ApiOperation("返回购买页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnBuyMessage(@RequestHeader("token") String token, Integer subjectId) throws Exception {
        log.info("进入返回购买页面信息方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE) || StringUtils.isEmpty(user.getLevel())) {
            return new ResultModel().error(-5, "必须是借款人才能操作");
        }
        ResultModel resultModel = accountManageService.returnBuyMessage(token, subjectId);
        log.info("结束返返回购买页面信息方法");
        return resultModel;
    }

    @PostMapping("buySubject")
    @ApiOperation("立即购买")
    public ResultModel buySubject(@RequestHeader("token") String token, @RequestBody AccountManage accountManage) throws Exception {
        log.info("进入立即购买方法，金额：{}，支付密码：{}", accountManage.getPrice(), accountManage.getPayPassword());
        Assert.notNull(accountManage.getPrice(), "金额不能为空");
        if (accountManage.getPrice().compareTo(new BigDecimal("100.00")) == -1 || accountManage.getPrice().divideAndRemainder(new BigDecimal("100.00"))[1].compareTo(new BigDecimal(0)) > 0) {
            return new ResultModel().error(-1, "金额只能是100的整数,并且不能小于100");
        }
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE) || StringUtils.isEmpty(user.getLevel())) {
            return new ResultModel().error(-5, "必须是投资人才能操作");
        }
        Assert.hasText(accountManage.getPayPassword(), "交易密码不能为空");
        Assert.state(accountManage.getPayPassword().equals(userApi.returnPayPassword(token)), "支付密码错误");
        accountManageService.buySubject(token, accountManage);
        log.info("结束立即购买方法");
        return new ResultModel().success();
    }

    @PostMapping("returnBorrowMessage")
    @ApiOperation("返回我的借款页面信息")
    public ResultModel returnBorrowMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回我的借款页面信息方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.BORROWER_CODE) || StringUtils.isEmpty(user.getLevel())) {
            return new ResultModel().error(-5, "必须是借款人才能操作");
        }
        ResultModel resultModel = accountManageService.returnBorrowMessage(token);
        log.info("结束返回我的借款页面信息方法");
        return resultModel;
    }

    @PostMapping("sign")
    @ApiOperation("签约")
    public ResultModel sign(@RequestHeader("token") String token, Integer subjectId) throws Exception {
        log.info("进入签约方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.BORROWER_CODE) || StringUtils.isEmpty(user.getLevel())) {
            return new ResultModel().error(-5, "必须是借款人才能操作");
        }
        ResultModel resultModel = accountManageService.sign(token, subjectId);
        log.info("结束签约方法");
        return resultModel;
    }

    @PostMapping("saveBorrowerPrice")
    @ApiOperation("存入借款人账户")
    public ResultModel saveBorrowerPrice(@RequestHeader("token") String token, Integer userId, Integer subjectId) throws Exception {
        log.info("进入存入借款人账户方法");
        ResultModel resultModel = accountManageService.saveBorrowerPrice(token, userId, subjectId);
        log.info("结存入借款人账户方法");
        return resultModel;
    }

    @PostMapping("generatedBills")
    @ApiOperation("借款人生成账单")
    public ResultModel generatedBills(@RequestHeader("token") String token, @RequestBody Subject subject) throws Exception {
        log.info("进入借款人生成账单方法");
        billService.generatedBills(subject);
        log.info("结束借款人生成账单方法");
        return new ResultModel().success();
    }

    @PostMapping("ReadBills")
    @ApiOperation("借款人查看账单")
    public ResultModel readBills(@RequestHeader("token") String token, Integer subjectId) throws Exception {
        log.info("进入借款人查看账单方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.BORROWER_CODE) || StringUtils.isEmpty(user.getLevel())) {
            return new ResultModel().error(-5, "必须是借款人人才能操作");
        }
        ResultModel resultModel = billService.readBills(subjectId);
        log.info("结束借款人查看账单方法");
        return resultModel;
    }

    @PostMapping("investorsReadBills")
    @ApiOperation("投资人查看账单")
    public ResultModel investorsReadBills(@RequestHeader("token") String token, Integer subjectId) throws Exception {
        log.info("进入投资人查看账单方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE) || StringUtils.isEmpty(user.getLevel())) {
            return new ResultModel().error(-5, "必须是投资人才能操作");
        }
        ResultModel resultModel = billService.investorsReadBills(subjectId,user.getId());
        log.info("结束投资人查看账单方法");
        return resultModel;
    }

    @PostMapping("rePayBill")
    @ApiOperation("还款")
    public ResultModel waitRePay(@RequestHeader("token") String token, Integer billId) throws Exception {
        log.info("进入还款方法,id:{}", billId);
        billService.rePayBill(token, billId);
        log.info("结束还款方法");
        return new ResultModel().success();
    }

    @PostMapping("returnMyOutBorrow")
    @ApiOperation("返回我的出借页面信息")
    public ResultModel returnMyOutBorrow(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回我的出借页面信息方法");
        ResultModel resultModel = accountManageService.returnMyOutBorrow(token);
        log.info("结束返回我的出借页面信息方法");
        return resultModel;
    }

    @PostMapping("returnAllRePayPrice")
    @ApiOperation("返回所有待还金额")
    public ResultModel returnAllRePayPrice(@RequestHeader("token")String token) throws Exception{
        log.info("进入返回我的出借页面信息方法");
        ResultModel resultModel = billService.returnAllRepayPrice();
        log.info("结束返回我的出借页面信息方法");
        return resultModel;
    }
}


