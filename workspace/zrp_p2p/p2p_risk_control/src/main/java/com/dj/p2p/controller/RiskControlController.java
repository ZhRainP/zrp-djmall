package com.dj.p2p.controller;


import com.dj.p2p.client.DictApi;
import com.dj.p2p.client.UserApi;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.riskcontrol.P2pExaTra;
import com.dj.p2p.riskcontrol.P2pRecheck;
import com.dj.p2p.riskcontrol.RiskControl;
import com.dj.p2p.service.RiskControlService;
import com.dj.p2p.user.User;
import com.dj.p2p.user.UserDetail;
import com.dj.p2p.util.DictConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "风控api")
@RestController
@RequestMapping(value = "risk", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class RiskControlController {

    @Autowired
    private DictApi dictApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private RiskControlService riskControlService;

    @Autowired
    private RedisTemplate redisTemplate;
    @ApiOperation("去发标查询字典")
    @PostMapping("toBid")
    public ResultModel toBid() throws BusinessException {
        return dictApi.toBid();
    }

    @ApiOperation("关联借款人")
    @PostMapping("findAllBorrower")
    public ResultModel findAllBorrower() throws BusinessException {
        return userApi.findAllBorrower();
    }



    @ApiOperation("发布标的")
    @PostMapping("release")
    public ResultModel release (@RequestHeader("token") String token, @RequestBody RiskControl riskControl) throws BusinessException {
        log.info("进入发布标的");
        User user = (User) redisTemplate.opsForValue().get(token);
        if(!user.getLevel().equals(DictConstant.COMMISSIONER)){
            return new ResultModel().error(300, "发标失败","使用身份不匹配，请使用风控专员账号登陆");
        }
        Assert.hasText(riskControl.getProType(), "请选择产品名");
        Assert.hasText(riskControl.getIsShowTarget(), "是否显示标的不能为空");
        Assert.hasText(riskControl.getProTargetType(), "标的类型不能为空");
        Assert.notNull(riskControl.getBorrowerId(), "关联借款人不能为空");
        Assert.hasText(riskControl.getProMoney().toString(), "金额不能为空");
        Assert.hasText(riskControl.getProLimitMoney(), "单人限额不能为空");
        Assert.notNull(riskControl.getProApr(), "年利率不能为空");
        Assert.notNull(riskControl.getProDeadline(), "期限不能为空");
        Assert.notNull(riskControl.getProRepType(), "还款方式不能为空");
        Assert.notNull(riskControl.getProName(), "产品名不能为空");
        Assert.notNull(riskControl.getProExp(), "借款说明不能为空");
        riskControlService.insertMark(riskControl);
        log.info("进入发布标的结束");
        return new ResultModel().success("发布成功");
    }

    @ApiOperation("风控中心")
    @PostMapping("riskControlCenter")
    public ResultModel riskControlCenter () throws BusinessException {
        List<RiskControl> riskControlList = riskControlService.getList();
        return new ResultModel().success(riskControlList);
    }

    @PostMapping("toFirstTrial")
    @ApiOperation(value="去初审")
    public ResultModel toFirstTrial(@RequestHeader("token") String token, @RequestParam("id") Integer id) throws Exception{
        log.info("查询初审所需信息");
        User user = (User) redisTemplate.opsForValue().get(token);
        if(!user.getLevel().equals(DictConstant.MANAGER)){
            return new ResultModel().error(300, "审核失败","使用身份不匹配，请使用风控经理账号登陆");
        }
        ResultModel resultModel = riskControlService.toFirstTrial(id);
        log.info("查询初审信息结束");
        return new ResultModel().success();
    }

    @PostMapping("firstTrial")
    @ApiOperation(value="初审")
    public ResultModel firstTrial(@RequestHeader("token") String token, @RequestBody P2pExaTra p2pExaTra, @RequestParam("id") Integer id) throws Exception{
        log.info("查询初审信息");
        ResultModel resultModel=riskControlService.firstTrial(token, p2pExaTra, id);
        log.info("查询初审信息结束");
        return resultModel;
    }

    @PostMapping("toSecondTrial")
    @ApiOperation(value="去复审")
    public ResultModel toSecondTrial(@RequestHeader("token") String token,@RequestParam("id") Integer id) throws Exception{
        log.info("查询去复审所需信息");
        User user = (User) redisTemplate.opsForValue().get(token);
        if(!user.getLevel().equals(DictConstant.MAJORDOMO)){
            return new ResultModel().error(300, "审核失败","使用身份不匹配，请使用风控总监账号登陆");
        }
        ResultModel resultModel = riskControlService.toSecondTrial(token,id);
        log.info("查询去复审信息结束");
        return new ResultModel().success();
    }

    @PostMapping("secondTrial")
    @ApiOperation(value="复审")
    public ResultModel secondTrial(@RequestHeader("token") String token, @RequestBody P2pRecheck p2pRecheck,@RequestParam("id") Integer id) throws Exception{
        log.info("查询复审信息");
        ResultModel resultModel=riskControlService.secondTrial(token, p2pRecheck, id);
        log.info("查询复审信息结束");
        return resultModel;
    }

}

