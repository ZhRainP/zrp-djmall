package com.dj.p2p.controller;


import com.dj.p2p.client.AccountManageApi;
import com.dj.p2p.client.DictApi;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.constant.DictConstant;
import com.dj.p2p.constant.UserConstant;
import com.dj.p2p.pojo.dict.Dict;
import com.dj.p2p.pojo.openaccount.OpenAccount;
import com.dj.p2p.pojo.user.User;
import com.dj.p2p.service.OpenAccountService;
import com.dj.p2p.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ldm
 * @since 2020-11-26
 */
@RestController
@RequestMapping(value = "/user/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户API")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DictApi dictApi;

    @Autowired
    private OpenAccountService openAccountService;

    @Autowired
    private AccountManageApi accountManageApi;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    public ResultModel login(String phone, String password) throws Exception {
        log.info("进入用户登录方法，phone:{},password:{}", phone, password);
        Assert.hasText(phone, "手机号不能为空");
        ResultModel result = userService.getUserByUsernameAndPassword(phone, password);
        Assert.hasText(password, "密码不能为空");
        log.info("结束用户登陆方法");
        return result;
    }

    @PostMapping("returnAddUserMessage")
    @ApiOperation(value = "返回普通用户注册页面所需信息")
    public ResultModel returnAddUserMessage() throws Exception {
        log.info("进入返回普通用户注册页面所需信息方法");
        //性别
        List<Dict> sexList = dictApi.selectDictByParentCode(DictConstant.SEX_CODE);
        //学历
        List<Dict> educationList = dictApi.selectDictByParentCode(DictConstant.EDUCATION_CODE);
        //婚姻
        List<Dict> marriageList = dictApi.selectDictByParentCode(DictConstant.MARRIAGE_CODE);
        //工作年限
        List<Dict> workYearList = dictApi.selectDictByParentCode(DictConstant.WORK_YEAR_CODE);
        //房产
        List<Dict> homeProductionList = dictApi.selectDictByParentCode(DictConstant.HOME_PRODUCTION_CODE);
        //年收入
        List<Dict> yearPriceList = dictApi.selectDictByParentCode(DictConstant.YEAR_PRICE_CODE);
        //车产
        List<Dict> carProductionList = dictApi.selectDictByParentCode(DictConstant.CAR_PRODUCTION_CODE);
        Map<String, Object> map = new HashMap<>();
        map.put("sexList", sexList);
        map.put("educationList", educationList);
        map.put("marriageList", marriageList);
        map.put("workYearList", workYearList);
        map.put("homeProductionList", homeProductionList);
        map.put("yearPriceList", yearPriceList);
        map.put("carProductionList", carProductionList);
        log.info("结束返回普通用户注册页面所需信息方法");
        return new ResultModel().success(map);
    }

    @PostMapping("addUser")
    @ApiOperation(value = "普通用户注册")
    public ResultModel addUser(@RequestBody User user) throws Exception {
        log.info("进入普通用户注册方法，user:{}", user);
        Assert.hasText(user.getPhone(), "手机号不能为空");
        Assert.hasText(user.getPassword(), "密码不能为空");
        Assert.hasText(user.getRepeatPassword(), "重复密码不能为空");
        Assert.hasText(user.getRepeatPassword(), "重复密码不能为空");
        Assert.state(user.getPassword().equals(user.getRepeatPassword()), "两次输入密码不一致");
        Assert.hasText(user.getSex(), "必须选择一个性别");
        Assert.notNull(user.getAge(), "年龄不能为空");
        Assert.hasText(user.getMarriage(), "必须选择一个婚姻情况");
        Assert.hasText(user.getHomeProduction(), "必须选择一个房产情况");
        Assert.notNull(user.getAssetsValuation(), "资产估值不能为空");
        Assert.hasText(user.getCarProduction(), "车产信息不能为空");
        ResultModel resultModel = userService.addUser(user);
        log.info("结束普通用户注册方法");
        return resultModel;
    }

    @PostMapping("returnAddCompanyUserMessage")
    @ApiOperation(value = "返回公司用户注册页面所需信息")
    public ResultModel returnAddCompanyUserMessage() throws Exception {
        log.info("进入返回公司用户注册页面所需信息方法");
        List<Dict> userLevelList = dictApi.selectDictByParentCode(DictConstant.COMPANY_USER_LEVEL_CODE);
        Map<String, Object> map = new HashMap<>();
        map.put("userLevelList", userLevelList);
        log.info("结束返回公司用户注册页面所需信息方法");
        return new ResultModel().success(map);
    }

    @PostMapping("addCompanyUser")
    @ApiOperation(value = "新增公司用户")
    public ResultModel addCompanyUser(@RequestBody User user) throws Exception {
        log.info("进入公司用户注册方法，user:{}", user);
        Assert.hasText(user.getPhone(), "手机号不能为空");
        Assert.hasText(user.getPassword(), "密码不能为空");
        Assert.hasText(user.getRepeatPassword(), "重复密码不能为空");
        Assert.state(user.getPassword().equals(user.getRepeatPassword()), "两次输入密码不一致");
        Assert.hasText(user.getLevel(), "必须选择公司用户级别");
        ResultModel resultModel = userService.addCompanyUser(user);
        log.info("结束公司用户注册方法");
        return resultModel;
    }

    @PostMapping("userCertification")
    @ApiOperation(value = "用户实名认证")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel userCertification(@RequestBody OpenAccount openAccount, @RequestHeader("token") String token) throws Exception {
        log.info("进入用户实名认证方法，真实姓名：{}，身份证：{}", openAccount);
        Assert.hasText(openAccount.getRealName(), "真实姓名不能为空");
        Assert.hasText(openAccount.getIdCard(), "身份证不能为空");
        userService.userCertification(openAccount.getRealName(), openAccount.getIdCard(), token);
        log.info("结束用户实名认证方法");
        return new ResultModel().success();
    }

    @PostMapping("returnOpenAccountMessage")
    @ApiOperation(value = "返回用户开户页面所需信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnOpenAccountMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回用户开户页面所需信息方法");
        List<Dict> bankList = dictApi.selectDictByParentCode(DictConstant.BANK_CODE);
        List<Dict> levelList = dictApi.selectDictByParentCode(DictConstant.USER_LEVEL_CODE);
        Map<String, Object> map = new HashMap<>();
        map.put("bankList", bankList);
        map.put("levelList", levelList);
        log.info("结束返回用户开户页面所需信息方法");
        return new ResultModel().success(map);
    }

    @PostMapping("returnUserCertificationMessage")
    @ApiOperation(value = "返回用户实名认证信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnUserOpenAccountMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回用户实名认证信息方法");
        ResultModel resultModel = openAccountService.returnUserOpenAccountMessage(token);
        log.info("结束返回用户实名认证信息方法");
        return resultModel;
    }

    @PostMapping("userOpenAccount")
    @ApiOperation(value = "用户开户方法")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel userOpenAccount(@RequestBody OpenAccount openAccount, @RequestHeader("token") String token) throws Exception {
        log.info("进入用户开户信息方法，开户信息：{}", openAccount);
        Assert.hasText(openAccount.getRealName(), "客户名称不能为空");
        Assert.hasText(openAccount.getIdCard(), "身份证信息不能为空");
        Assert.hasText(openAccount.getBankNumber(), "身份证信息不能为空");
        Assert.hasText(openAccount.getLevel(), "账户类型不能为空");
        Assert.hasText(openAccount.getReservedPhone(), "预留手机号不能为空");
        Assert.hasText(openAccount.getPayPassword(), "支付密码不能为空");
        Assert.state(openAccount.getRepeatPayPassword().equals(openAccount.getPayPassword()), "两次支付密码输入不一致");
        ResultModel resultModel = userService.userOpenAccount(openAccount, openAccount.getLevel(), token);
        //创建钱表
        accountManageApi.addDefaultAccountManage(token);
        log.info("结束用户开户信息方法");
        return resultModel;
    }

    @PostMapping("userList")
    @ApiOperation(value = "用户列表展示")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel userList(@RequestHeader("token") String token) throws Exception {
        log.info("进入用户列表展示方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        Assert.state(user.getLevel().equals(UserConstant.DIRECTOR_RISK_CONTROL_CODE),"您不是管理员");
        ResultModel resultModel = userService.getUserList();
        log.info("结束用户列表展示方法");
        return resultModel;
    }

    @PostMapping("updateUserLockStatus")
    @ApiOperation(value = "修改用户锁定状态")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel updateUserLockStatus(@RequestHeader("token") String token, @RequestBody User user) throws Exception {
        log.info("进入修改用户锁定状态方法");
        User redisUser = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        Assert.state(redisUser.getLevel().equals(UserConstant.DIRECTOR_RISK_CONTROL_CODE),"您不是管理员");
        Assert.notNull(user.getId(), "用户ID不能为空");
        Assert.hasText(user.getIsLock(), "锁定状态不能为空");
        userService.updateLockStatus(user.getId(), user.getIsLock());
        log.info("结束修改用户锁定状态方法");
        return new ResultModel().success();
    }

    @PostMapping("securityCenter")
    @ApiOperation(value = "返回安全中心页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel securityCenter(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回安全中心页面信息方法");
        ResultModel resultModel = userService.getUserById(token);
        log.info("结束返回安全中心页面信息方法");
        return resultModel;
    }

    @PostMapping("returnHomePageMessage")
    @ApiOperation(value = "返回首页页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnHomePageMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回首页页面信息方法");
        ResultModel resultModel = userService.getHomePageMessage();
        log.info("结束返回首页页面信息方法");
        return resultModel;
    }

    @PostMapping("returnBankNumber")
    @ApiOperation("返回银行卡号信息（后台使用）")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public String withdraw(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回银行卡号信息方法");
        String bankNumber = userService.withdrawMessage(token);
        log.info("结束返返回银行卡号信息方法");
        return bankNumber;
    }

    @PostMapping("returnPayPassword")
    @ApiOperation("返回支付密码信息（后台使用）")
    public String returnPayPassword(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回支付密码信息方法");
        String payPassword = userService.getPayPassword(token);
        log.info("结束返回支付密码信息方法");
        return payPassword;
    }

    @PostMapping("returnBorrower")
    @ApiOperation("返回借款人信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnBorrower(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回借款人信息方法");
        ResultModel resultModel = userService.getBorrower();
        log.info("结束返回借款人信息方法");
        return resultModel;
    }
}

