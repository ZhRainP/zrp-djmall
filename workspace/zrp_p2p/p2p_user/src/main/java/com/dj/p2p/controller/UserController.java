package com.dj.p2p.controller;


import com.dj.p2p.client.DictApi;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.service.UserDetailService;
import com.dj.p2p.service.UserService;
import com.dj.p2p.service.UserWalletService;
import com.dj.p2p.user.User;
import com.dj.p2p.user.UserDetail;
import com.dj.p2p.user.UserLogin;
import com.dj.p2p.user.UserWallet;
import com.dj.p2p.util.DictConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Api(tags = "用户api")
@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DictApi dictApi;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private UserWalletService userWalletService;


    @ApiOperation("字典列表")
    @PostMapping("findList")
    public Map<String, Object> findList() throws BusinessException {
        Map<String, Object> map = dictApi.findList();
        return map;
    }

    @ApiOperation("用户登陆")
    @PostMapping("login")
    public ResultModel login(String phone, String password) throws BusinessException {
        Assert.hasText(phone, "手机号不能为空");
        Assert.hasText(password, "密码不能为空");
        log.info("进入用户登陆");
        return userService.findPhoneAndPassword(phone, password);
    }

    @ApiOperation("风控用户注册")
    @PostMapping("registerAdmin")
    public ResultModel registerAdmin(@RequestBody User user) throws BusinessException {
        Assert.hasText(user.getPhone(), "手机号不能为空");
        Assert.hasText(user.getPassword(), "密码不能为空");x
        Assert.state(user.getPassword().equals(user.getConfirmPassword()), "两次密码不一致");
        log.info("进入注册风控人员：{}", user);
        userService.registerAdmin(user);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token = UUID.randomUUID().toString().replace("-", "");
        valueOperations.set(token, user);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        log.info("进入注册风控人员结束:{}", user);
        return new ResultModel().success(map);
    }

    @ApiOperation("普通用户注册")
    @PostMapping("registerUser")
    public ResultModel registerUser(@RequestBody User user) throws BusinessException {
        Assert.hasText(user.getPhone(), "手机号不能为空");
        Assert.hasText(user.getPassword(), "密码不能为空");
        Assert.state(user.getPassword().equals(user.getConfirmPassword()), "两次密码不一致");
        Assert.hasText(user.getSex(), "性别不能为空");
        Assert.notNull(user.getAge(), "年龄不能为空");
        Assert.hasText(user.getEducation(), "学历不能为空");
        Assert.hasText(user.getMaritalStatus(), "婚姻不能为空");
        Assert.hasText(user.getWorkExperience(), "工作年限不能为空");
        Assert.hasText(user.getAssetValuation().toString(), "资产估计不能为空");
        Assert.hasText(user.getCarProperty(), "车产不能为空");
        Assert.hasText(user.getHouseProperty(), "房产不能为空");
        Assert.hasText(user.getAnnualIncome(), "年收入不能为空");
        log.info("进入注册普通用户：{}", user);
        userService.registerAdmin(user);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token = UUID.randomUUID().toString().replace("-", "");
        valueOperations.set(token, user);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        log.info("进入注册普通用户结束：{}", user);
        return new ResultModel().success(map);
    }

    @ApiOperation("检查手机号是否被注册")
    @PostMapping("findByPhone")
    public boolean findByPhone(String phone) throws BusinessException {
        log.info("进入手机号检查方法，{}", phone);
        boolean check = userService.findByPhone(phone);
        log.info("进入手机号检查方法结束，{}", phone);
        return check;
    }

    @ApiOperation("实名认证")
    @PostMapping("realName")
    public ResultModel realName(@RequestHeader String token, @RequestBody UserDetail userDetail) throws BusinessException {
        log.info("进入实名认证方法");
        User users = (User) redisTemplate.opsForValue().get(token);
        userDetail.setUserId(users.getId());
        userDetailService.realName(userDetail);
        log.info("进入实名认证方法结束");
        return new ResultModel().success("保存成功");
    }

    @ApiOperation("去开户查用户名身份证号")
    @PostMapping("openCount")
    public ResultModel openCount(@RequestHeader String token, @RequestBody UserDetail userDetail) throws BusinessException {
        log.info("进入查询用户名身份证号");
        Map<String, Object> map = new HashMap<>();
        User users = (User) redisTemplate.opsForValue().get(token);
        UserDetail user = userDetailService.openCount(users.getId());
        map.put("user", user);
        log.info("进入查询用户名身份证号结束");
        return new ResultModel().success(map);
    }

    @ApiOperation("去开户查找银行/账户类型")
    @PostMapping("toOpenCount")
    public ResultModel toOpenCount() throws BusinessException {
        return dictApi.toOpenCount();
    }

    @ApiOperation("提交开户信息")
    @PostMapping("insertOpenCount")
    public ResultModel insertOpenCount(@RequestHeader String token, @RequestBody UserDetail userDetail) throws BusinessException {
        log.info("进入提交开户信息");
        if(userDetail.getOpenStatus().equals(DictConstant.BANK_OPEN_STATUS)){
            return new ResultModel().error(300, "操作失败", "请勿重新开户");
        }
        Assert.hasText("银行卡号不能为空", userDetail.getBankCard());
        Assert.hasText("请选择账户类型", userDetail.getAccountType());
        Assert.hasText("银行预留手机号不能为空", userDetail.getBankPhone());
        Assert.hasText("请设置交易密码", userDetail.getTransactionPwd());
        Assert.state(userDetail.getTransactionPwd().equals(userDetail.getConfirmTransactionPwd()), "两次密码不一致");
        userDetailService.insertOpenCount(userDetail, token);
        UserDetail users = (UserDetail) redisTemplate.opsForValue().get(token);
        userWalletService.insertBalance(users.getUserId());
        log.info("进入提交开户信息结束");
        return new ResultModel().success("开户成功");
    }

    @ApiOperation("安全中心列表")
    @PostMapping("safeCenter")
    public ResultModel safeCenter(@RequestHeader String token) throws BusinessException {
        log.info("进入安全中心展示列表");
        HashMap<String, Object> map = new HashMap<>();
        User users = (User) redisTemplate.opsForValue().get(token);
        UserDetail userDetail = userDetailService.getUserDetailByUserId(users.getId());
        if (userDetail == null || userDetail.getOpenStatus() == null) {
            return new ResultModel().error(300, "业务处理失败", "还未做实名认证，不能进行此操作");
        }
        List<UserDetail> safeCenterList = userDetailService.getSafeCenterList();
        map.put("safeCenter", safeCenterList);
        log.info("进入安全中心展示列表结束");
        return new ResultModel().success(safeCenterList);
    }

    @ApiOperation("后台用户信息列表")
    @PostMapping("userInformation")
    public ResultModel userInformation() throws BusinessException {
        log.info("进入后台用户信息列表");
        HashMap<String, Object> map = new HashMap<>();
        List<User> userInformationList = userService.userInformation();
        map.put("userInformation", userInformationList);
        log.info("进入后台用户信息列表结束");
        return new ResultModel().success(map);
    }

    @ApiOperation("用户状态锁定/未锁定")
    @PostMapping("updateUserStatus")
    public ResultModel updateUserStatus(@RequestBody User user) throws Exception {
        userService.updateUserStatus(user);
        return new ResultModel().success("操作成功");
    }

    @ApiOperation("获取用户钱包信息")
    @PostMapping("getUserWallet")
    public ResultModel getUserWallet(@RequestHeader String token) throws BusinessException {
        log.info("进入获取用户钱包信息");
        HashMap<String, Object> map = new HashMap<>();
        UserDetail users = (UserDetail) redisTemplate.opsForValue().get(token);
        if(!users.getOpenStatus().equals(DictConstant.BANK_NOT_OPEN_STATUS)){
            return new ResultModel().error(300, "操作失败","还未开户，请先去开户");
        }
        UserWallet userWallet = userWalletService.getUserWallet(users.getId());
        map.put("userWallet", userWallet);
        log.info("进入获取用户钱包信息");
        return new ResultModel().success(map);
    }

    @ApiOperation("去充值页面")
    @PostMapping("toRecharge")
    public ResultModel toRecharge(@RequestHeader String token) throws BusinessException {
        log.info("进入用户充值页面");
        HashMap<String, Object> map = new HashMap<>();
        UserDetail users = (UserDetail) redisTemplate.opsForValue().get(token);
        if(users.getOpenStatus().equals(DictConstant.BANK_NOT_OPEN_STATUS)){
            return new ResultModel().error(300, "操作失败","还未开户，请先去开户");
        }
        UserWallet userWallet = userWalletService.getUserWallet(users.getUserId());
        map.put("userWallet", userWallet);
        log.info("进入用户充值页面结束");
        return new ResultModel().success(map);
    }

    @ApiOperation("充值余额")
    @PostMapping("rechargeBalance")
    public ResultModel rechargeBalance(@RequestHeader("token") String token, String transactionPwd, BigDecimal rechargeAmount) throws BusinessException {
        log.info("进入充值余额接口");
        Assert.notNull(rechargeAmount, "充值金额不能为空");
        Assert.hasText(transactionPwd, "交易密码不能为空");
        HashMap<String, Object> map = new HashMap<>();
        UserDetail users = (UserDetail) redisTemplate.opsForValue().get(token);
        if(users.getOpenStatus().equals(DictConstant.BANK_NOT_OPEN_STATUS)){
            return new ResultModel().error(300, "操作失败","还未开户，请先去开户");
        }
        UserDetail userDetail = userDetailService.getUserDetailByUserId(users.getUserId());
        map.put("userDetail", userDetail);
        //交易密码校验
        if (!userDetail.getTransactionPwd().equals(transactionPwd)) {
            return new ResultModel().error("交易密码输入错误");
        }
        //最低充值金额校验
        BigDecimal bigDecimal = new BigDecimal(10.00);
        if (rechargeAmount.compareTo(bigDecimal) == -1) {
            return new ResultModel().error("充值金额不能低于10元");
        }
        UserWallet userWallet = userWalletService.getUserWallet(users.getUserId());
        userWallet.setBalance(userWallet.getBalance().add(rechargeAmount));
        Boolean status = userWalletService.updateWallet(userWallet);
        if (status) {
            log.info("进入充值余额接口结束");
            return new ResultModel().success("充值成功");
        }
        return new ResultModel().error("充值失败");
    }

    @ApiOperation("提现展示")
    @PostMapping("withdrawalList")
    public ResultModel withdrawalList(@RequestHeader String token) throws BusinessException {
        log.info("进入提现展示");
        HashMap<String, Object> map = new HashMap<>();
        UserDetail users = (UserDetail) redisTemplate.opsForValue().get(token);
        if(users.getOpenStatus().equals(DictConstant.BANK_NOT_OPEN_STATUS)){
            return new ResultModel().error(300, "操作失败","还未开户，请先去开户");
        }
        UserDetail userDetail = userDetailService.getWithdrawalList(users.getUserId());
        map.put("userDetail", userDetail);
        log.info("进入提现展示结束");
        return new ResultModel().success(map);
    }

    @ApiOperation("提现")
    @PostMapping("withdrawal")
    public ResultModel withdrawal(@RequestHeader("token") String token, String transactionPwd, BigDecimal withdrawalAmount) throws BusinessException {
        log.info("进入提现接口");
        Assert.notNull(withdrawalAmount, "提现金额不能为空");
        Assert.hasText(transactionPwd, "交易密码不能为空");
        UserDetail users = (UserDetail) redisTemplate.opsForValue().get(token);
        if(users.getOpenStatus().equals(DictConstant.BANK_NOT_OPEN_STATUS)){
            return new ResultModel().error(300, "操作失败","还未开户，请先去开户");
        }
        UserDetail userDetail = userDetailService.getUserDetailByUserId(users.getUserId());
        if (!userDetail.getTransactionPwd().equals(transactionPwd)) {
            return new ResultModel().error(100, "业务处理失败", "交易密码输入错误");
        }
        UserWallet userWallet = userWalletService.getUserWallet(users.getId());
        BigDecimal balance = userWallet.getBalance().subtract(withdrawalAmount);
        if (balance.intValue() < 0) {
            return new ResultModel().error(400, "业务处理失败", "余额不足");
        }
        userWallet.setBalance(balance);
        Boolean status = userWalletService.updateWallet(userWallet);
        if (status) {
            log.info("进入提现接口结束");
            return new ResultModel().success("提现成功");
        }
        return new ResultModel().error("充值失败");
    }

    @ApiOperation("查找借款人")
    @PostMapping("findAllBorrower")
    public ResultModel findAllBorrower() throws BusinessException {
        List<UserDetail> userDetails = userDetailService.findAllBorrower();
        return new ResultModel().success(userDetails);
    }

    @ApiOperation("通过id查找借款人信息")
    @PostMapping("findBorrowerByUserId")
    public ResultModel findBorrowerByUserId (@RequestParam("userId") Integer userId) throws BusinessException {
        log.info("进入根据ID查找借款人信息");
        UserDetail userDetail = userDetailService.findBorrowerByUserId(userId);
        log.info("进入根据ID查找借款人信息结束");
         return new ResultModel().success(userDetail);
    }

}

