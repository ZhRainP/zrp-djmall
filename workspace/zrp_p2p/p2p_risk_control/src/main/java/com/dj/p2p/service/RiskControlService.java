package com.dj.p2p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.riskcontrol.P2pExaTra;
import com.dj.p2p.riskcontrol.P2pRecheck;
import com.dj.p2p.riskcontrol.RiskControl;
import com.dj.p2p.user.UserLogin;

import java.util.List;

public interface RiskControlService extends IService<RiskControl>  {


    void insertMark(RiskControl riskControl) throws BusinessException;

    List<RiskControl> getList() throws BusinessException;

    ResultModel toFirstTrial(Integer id) throws BusinessException;

    ResultModel firstTrial(String token, P2pExaTra p2pExaTra, Integer id) throws BusinessException;

    ResultModel toSecondTrial(String token, Integer id) throws BusinessException;

    ResultModel secondTrial(String token, P2pRecheck p2pRecheck, Integer id) throws BusinessException;
}
