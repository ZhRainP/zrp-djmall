package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.client.DictApi;
import com.dj.p2p.client.UserApi;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.dict.Dictionary;
import com.dj.p2p.mapper.RiskControlMapper;
import com.dj.p2p.riskcontrol.P2pExaTra;
import com.dj.p2p.riskcontrol.P2pRecheck;
import com.dj.p2p.riskcontrol.RiskControl;
import com.dj.p2p.service.IP2pExaTraService;
import com.dj.p2p.service.IP2pRecheckService;
import com.dj.p2p.service.RiskControlService;
import com.dj.p2p.user.User;
import com.dj.p2p.user.UserDetail;
import com.dj.p2p.util.DictConstant;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class RiskControlServiceImpl extends ServiceImpl<RiskControlMapper, RiskControl> implements RiskControlService {

    @Autowired
    private UserApi userApi;
    @Autowired
    private DictApi dictApi;
    @Autowired
    private IP2pExaTraService ip2pExaTraService;
    @Autowired
    private IP2pRecheckService ip2pRecheckService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void insertMark(RiskControl riskControl) throws BusinessException {
        BigDecimal b1 = new BigDecimal(20000);
        BigDecimal b2 = new BigDecimal(1000000);
        BigDecimal b3 = new BigDecimal(3);
        BigDecimal b4 = new BigDecimal(9);
        if (riskControl.getProMoney().compareTo(b1) == -1) {
            throw new BusinessException("金额不能小于20000");
        }
        if (riskControl.getProMoney().compareTo(b2) == 1) {
            throw new BusinessException("金额不能大于10000000");
        }
        if(riskControl.getProApr().compareTo(b3) == -1){
            throw new BusinessException("年利率不能小于3%");
        }
        if(riskControl.getProApr().compareTo(b4) == 1){
            throw new BusinessException("年利率不能大于9%");
        }
        riskControl.setProCaseMoney(new BigDecimal(100));
        super.save(riskControl);
    }

    @Override
    public List<RiskControl> getList() throws BusinessException {
        HashMap<String, Object> map = new HashMap<>();
        List<RiskControl> p2pFinancialList=super.baseMapper.getList();
        for(RiskControl f:p2pFinancialList){
            BigDecimal interestMoney = f.getProMoney().multiply(f.getProApr()).multiply(new BigDecimal(0.01));
            //利息
            f.setInterestMoney(interestMoney);
            //本息
            f.setInterestANDPrincipal(f.getProMoney().add(interestMoney));
        }
        map.put("p2pFinancial", p2pFinancialList);
        return p2pFinancialList;
    }

    @Override
    public ResultModel toFirstTrial(Integer id) throws BusinessException {
        HashMap<String, Object> map = new HashMap<>();
        RiskControl p2pFinancial=super.baseMapper.getP2pFinancial(id);
        BigDecimal interestMoney = p2pFinancial.getProMoney().multiply(p2pFinancial.getProApr()).multiply(new BigDecimal(0.01));
        //利息
        p2pFinancial.setInterestMoney(interestMoney);
        //本息
        p2pFinancial.setInterestANDPrincipal(p2pFinancial.getProMoney().add(interestMoney));
        //发标信息
        map.put("p2pFinancial",p2pFinancial);
        //借款人信息
        ResultModel resultModel = userApi.findBorrowerByUserId(p2pFinancial.getBorrowerId());
        HashMap<String, Object> userDetailMap = new HashMap<>();
        userDetailMap.put("userDetail", resultModel);
        return new ResultModel().success(map);
    }

    @Override
    public ResultModel firstTrial(String token, P2pExaTra p2pExaTra, Integer id) throws BusinessException {
        //新增审批流程
        //token查找审批人
        User user = (User) redisTemplate.opsForValue().get(token);
        p2pExaTra.setExaUserId(user.getId());
        p2pExaTra.setExaProId(id);
        p2pExaTra.setExaType(DictConstant.FIRST_TRIAL);
        p2pExaTra.setExaTime(Date.from(Instant.now()));
        //保存审批流程信息
        ip2pExaTraService.save(p2pExaTra);
        //修改审批状态
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("pro_audit_status",p2pExaTra.getTrialType());
        updateWrapper.eq("id",id);
        super.update(updateWrapper);

        return new ResultModel().success("成功");
    }

    @Override
    public ResultModel toSecondTrial(String token, Integer id) throws BusinessException {
        //调用去初审查询所需信息
        ResultModel resultModel = this.toFirstTrial(id);
        Map<String,Object> map = (Map<String, Object>) resultModel.getData();
        //查询审批流程
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("exa_pro_id",id);
        List<P2pExaTra> p2pExaTraList = ip2pExaTraService.list(queryWrapper);
        map.put("p2pExaTraList",p2pExaTraList);
        HashMap<String, Object> maps = new HashMap<>();
        ResultModel ContractList =  dictApi.toFindContract();
        map.put("Contract", ContractList);
        return new ResultModel().success(map);
    }

    @Override
    public ResultModel secondTrial(String token, P2pRecheck p2pRecheck, Integer id) throws BusinessException {
        //修改审批状态
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("pro_audit_status",p2pRecheck.getTrialType());
        updateWrapper.set("pro_status",DictConstant.PS_BORROWING);// 借款中
        updateWrapper.eq("id",id);
        super.update(updateWrapper);
        //新增审批流程
        //token 查询用户
        User user = (com.dj.p2p.user.User) redisTemplate.opsForValue().get(token);
        //构建轨迹表
        ip2pExaTraService.save(P2pExaTra.builder().exaUserId(user.getId()).exaProId(p2pRecheck.getProId()).exaOpi(p2pRecheck.getExaOpi()).
                exaType(DictConstant.SECOND_TRIAL).exaTime(Date.from(Instant.now())).build());
        //新增复审信息表
        p2pRecheck.setReleaseTime(Date.from(Instant.now()));
        p2pRecheck.setProId(id);
        ip2pRecheckService.save(p2pRecheck);
        return new ResultModel().success("成功");
    }
}
