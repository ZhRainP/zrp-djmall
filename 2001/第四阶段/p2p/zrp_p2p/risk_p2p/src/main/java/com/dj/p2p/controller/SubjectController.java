package com.dj.p2p.controller;


import com.dj.p2p.client.UserApi;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.client.DictApi;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.constant.DictConstant;
import com.dj.p2p.pojo.openaccount.OpenAccount;
import com.dj.p2p.pojo.subject.Check;
import com.dj.p2p.pojo.subject.CheckRecord;
import com.dj.p2p.pojo.subject.Subject;
import com.dj.p2p.pojo.user.User;
import com.dj.p2p.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/subject/")
@Api(tags = "风控API")
@Slf4j
public class SubjectController {

    @Autowired
    private DictApi dictApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("returnPostSubjectMessage")
    @ApiOperation("返回发标页面所需信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnPostSubjectMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回发标页面所需信息方法");
        ResultModel dictResultModel = dictApi.returnDictMessage();
        ResultModel userResultModel = userApi.returnBorrower(token);
        Map<String, Object> returnMap = (Map<String, Object>) dictResultModel.getData();
        Map<String, Object> userMap = (Map<String, Object>) userResultModel.getData();
        returnMap.put("borrowerList", userMap.get("borrowerList"));
        log.info("结束返回发标页面所需信息方法");
        return new ResultModel().success(returnMap);
    }

    @PostMapping("createSubject")
    @ApiOperation("创建标的")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel createSubject(@RequestHeader("token") String token, @RequestBody Subject subject) throws Exception {
        log.info("进入创建标的方法");
        Assert.hasText(subject.getIsLookSubject(), "必须选择是否显示标的");
        Assert.hasText(subject.getSubjectType(), "必须选择标的类型");
        Assert.notNull(subject.getBorrowerId(), "必须选择关联借款人");
        Assert.notNull(subject.getSubjectMoney(), "必须输入金额");
        if (subject.getSubjectMoney().divideAndRemainder(new BigDecimal("100.00"))[1].compareTo(new BigDecimal(0)) > 0
                || subject.getSubjectMoney().compareTo(new BigDecimal("20000.00")) == -1
                || subject.getSubjectMoney().compareTo(new BigDecimal("100000.00")) == 1) {
            return new ResultModel().error("金额在20000元~1000000元之间并且是100的正整数倍");
        }
        Assert.notNull(subject.getYearInterest(), "年利率不能为空");
        Assert.hasText(subject.getProductName(), "项目名称不能为空");
        Assert.hasText(subject.getBorrowAdvice(), "借款说明不能为空");
        if (subject.getIsLimit().equals(DictConstant.SIGLE_CODE)) {
            Assert.notNull(subject.getSubjectLimitMoney(), "限额金额不能为空");
        }
        subjectService.createSubject(subject);
        log.info("结束创建标的方法");
        return new ResultModel().success();
    }

    @PostMapping("returnRiskMessage")
    @ApiOperation("风控页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnRiskMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入风控页面信息方法");
        List<Subject> subjectList = subjectService.riskMessage(null);
        Map<String, Object> map = new HashMap<>();
        map.put("subjectList", subjectList);
        log.info("结束风控页面信息方法");
        return new ResultModel().success(map);
    }

    @PostMapping("returnOneCheck")
    @ApiOperation("初审页面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户令牌"),
            @ApiImplicitParam(name = "userId", value = "用户ID")
    })
    public ResultModel returnOneCheck(@RequestHeader("token") String token, Integer userId) throws Exception {
        log.info("进入初审页面信息方法");
        List<Subject> subjectList = subjectService.riskMessage(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("subjectList", subjectList);
        log.info("结束初审页面信息方法");
        return new ResultModel().success(map);
    }

    @PostMapping("oneCheck")
    @ApiOperation("初审")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel oneCheck(@RequestHeader("token") String token, @RequestBody CheckRecord checkRecord) throws Exception {
        log.info("进入初审方法");
        Assert.notNull(checkRecord.getSubjectId(), "标的ID不能为空");
        Assert.hasText(checkRecord.getCheckOpinion(), "审批意见不能为空");
        Assert.hasText(checkRecord.getAuditStatus(), "审批结果不能为空");
        subjectService.oneCheck(checkRecord, token);
        log.info("结束初审方法");
        return new ResultModel().success();
    }

    @PostMapping("returnRepeatCheckMessage")
    @ApiOperation("返回复审页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel oneCheck(@RequestHeader("token") String token, Integer userId) throws Exception {
        log.info("进入返回复审页面信息方法");
        ResultModel resultModel = subjectService.returnRepeatCheckMessage(userId);
        log.info("结束返回复审页面信息方法");
        return resultModel;
    }

    @PostMapping("repeatCheck")
    @ApiOperation("复审")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel repeatCheck(@RequestHeader("token") String token, @RequestBody Check check) throws Exception {
        log.info("进入复审方法");
        Assert.notNull(check.getSubjectId(), "标的ID不能为空");
        Assert.notNull(check.getAdvanceRepayPenalty(), "提前还款违约金不能为空");
        Assert.notNull(check.getBreachContract(), "违约金不能为空");
        Assert.notNull(check.getOverdueFine(), "逾期罚息不能为空");
        Assert.hasText(check.getBorrowDurationPoundageType(), "借款存续期手续费计算方式不能为空");
        Assert.notNull(check.getBorrowDurationPoundage(), "借款存续期手续费不能为空");
        Assert.notNull(check.getFundraiseTime(), "筹款天数不能为空");
        Assert.notNull(check.getRiskAdvice(), "风控建议不能为空");
        Assert.hasText(check.getBorrowContract(), "借款合同不能为空");
        Assert.hasText(check.getCheckOpinion(), "审批意见不能为空");
        Assert.hasText(check.getAuditStatus(), "审批结果不能为空");
        subjectService.repeatCheck(check, token);
        log.info("结束复审方法");
        return new ResultModel().success();
    }

    @PostMapping("returnFinancialProjectMessage")
    @ApiOperation("返回理财项目页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnFinancialProjectMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回理财项目页面信息方法");
        ResultModel resultModel = subjectService.returnFinancialProjectMessage(token);
        log.info("结束返回理财项目页面信息方法");
        return resultModel;
    }

    @PostMapping("returnOutBorrowMessage")
    @ApiOperation("返回我要出借页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnOutBorrowMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回理财项目页面信息方法");
        ResultModel resultModel = subjectService.returnOutBorrowMessage();
        log.info("结束返回理财项目页面信息方法");
        return resultModel;
    }

    @PostMapping("returnBuyMessage")
    @ApiOperation("返回购买页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnBuyMessage(@RequestHeader("token") String token, Integer subjectId) throws Exception {
        log.info("进入返回购买信息方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE)) {
            throw new BusinessException(-4, "只有投资人才可访问，您无权限！");
        }
        ResultModel resultModel = subjectService.returnBuyMessage(subjectId);
        log.info("结束返回购买页面方法");
        return resultModel;
    }

    @PostMapping("buySubject")
    @ApiOperation("立即购买")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户令牌"),
            @ApiImplicitParam(name = "subjectId", value = "标的ID"),
            @ApiImplicitParam(name = "price", value = "金额")
    })
    public ResultModel buySubject(@RequestHeader("token") String token, Integer subjectId, BigDecimal price) throws Exception {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE)) {
            throw new BusinessException(-4, "只有投资人才可访问，您无权限！");
        }
        log.info("进入返回购买信息方法，标的ID：{}，金额：{}", subjectId, price);
        subjectService.buySubject(subjectId, price);
        log.info("结束返回购买页面方法");
        return new ResultModel().success();
    }

    @PostMapping("returnBorrowMessage")
    @ApiOperation("返回我的借款信息")
    public ResultModel returnBorrowMessage(@RequestHeader("token") String token, Integer userId) throws Exception {
        log.info("进入返回我的借款信息方法，用户ID为：{}", userId);
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.BORROWER_CODE)) {
            throw new BusinessException(-4, "只有借款人才可访问，您无权限！");
        }
        ResultModel resultModel = subjectService.returnBorrowMessage(userId);
        log.info("结束返回我的借款信息方法");
        return resultModel;
    }

    @PostMapping("sign")
    @ApiOperation("签约")
    public ResultModel sign(@RequestHeader("token") String token, Integer subjectId) throws Exception {
        log.info("进入签约方法，标的ID为：{}", subjectId);
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.BORROWER_CODE)) {
            throw new BusinessException(-4, "只有借款人才可访问，您无权限！");
        }
        subjectService.sign(subjectId);
        log.info("结束签约方法");
        return new ResultModel().success();
    }

    @PostMapping("advancePrice")
    @ApiOperation("放款")
    public ResultModel advancePrice(@RequestHeader("token") String token, Integer subjectId) throws Exception {
        log.info("进入放款方法，标的ID为：{}", subjectId);
        subjectService.advancePrice(token, subjectId);
        log.info("结束放款方法");
        return new ResultModel().success();
    }

    @PostMapping("overSubject")
    @ApiOperation("标的完成")
    public ResultModel overSubject(@RequestHeader("token") String token, Integer subjectId) throws Exception {
        log.info("进入标的完成方法，标的ID为：{}", subjectId);
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.BORROWER_CODE)) {
            throw new BusinessException(-4, "只有借款人才可访问，您无权限！");
        }
        subjectService.overSubject(subjectId);
        log.info("结束标的完成方法");
        return new ResultModel().success();
    }

    @PostMapping("returnMyOutBorrow")
    @ApiOperation("返回我的出借页面信息")
    public ResultModel returnMyOutBorrow(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回我的出借页面信息方法");
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        if (!user.getLevel().equals(DictConstant.INVESTORS_CODE)) {
            throw new BusinessException(-4, "只有投资人才可访问，您无权限！");
        }
        ResultModel resultModel = subjectService.returnMyOutBorrow(user.getId());
        log.info("结束返回我的出借页面信息方法");
        return resultModel;
    }

    @PostMapping("returnHomePageMessage")
    @ApiOperation(value = "返回首页页面信息")
    @ApiImplicitParam(name = "token", value = "用户令牌")
    public ResultModel returnHomePageMessage(@RequestHeader("token") String token) throws Exception {
        log.info("进入返回首页页面信息方法");
        ResultModel resultModel = subjectService.returnHomePageMessage(token);
//        ResultModel resultModel = userApi.returnHomePageMessage(token);
//        System.out.println(resultModel.getData());
        log.info("结束返回首页页面信息方法");
        return resultModel;
    }
}

