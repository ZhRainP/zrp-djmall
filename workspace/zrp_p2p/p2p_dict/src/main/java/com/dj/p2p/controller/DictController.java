package com.dj.p2p.controller;


import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.dict.Dictionary;
import com.dj.p2p.service.DictService;
import com.dj.p2p.util.DictConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "字典Api")
@RestController
@RequestMapping(value = "dict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class DictController {
    @Autowired
    private DictService dictService;

    @PostMapping("findList")
    public ResultModel findList () throws BusinessException {
        log.info("进入根据父级code查询列表");
        Map<String, Object> map = new HashMap<>();
        //性别
        List<Dictionary> sexList =  dictService.findList(DictConstant.USER_SEX);
        //学历
        List<Dictionary> educationList =  dictService.findList(DictConstant.EDUCATION);
        //婚姻
        List<Dictionary> marriageList =  dictService.findList(DictConstant.MARRIAGE);
        //工作年限
        List<Dictionary> workYearList =  dictService.findList(DictConstant.YEARSWORK);
        //房产
        List<Dictionary> houseList =  dictService.findList(DictConstant.HOUSE);
        //年收入
        List<Dictionary> annualIncomeList =  dictService.findList(DictConstant.ANNUAL_INCOME);
        //车产
        List<Dictionary> carProductionList =  dictService.findList(DictConstant.CAR_PRODUCTION);
        map.put("sexList", sexList);
        map.put("education", educationList);
        map.put("marriage", marriageList);
        map.put("workYear", workYearList);
        map.put("house", houseList);
        map.put("annualIncome", annualIncomeList);
        map.put("carProduction", carProductionList);
        log.info("进入根据父级code查询列表结束");
        return  new ResultModel().success(map);
    }

    @PostMapping("toOpenCount")
    public ResultModel toOpenCount () throws BusinessException {
        log.info("进入根据父级code查询银行/账户类型列表");
        Map<String, Object> map = new HashMap<>();
        //银行
        List<Dictionary> bankList =  dictService.findList(DictConstant.BANK_TYPE);
        //账户类型
        List<Dictionary> accountList =  dictService.findList(DictConstant.ACCOUNT_TYPE);
        map.put("bank", bankList);
        map.put("account", accountList);
        log.info("进入根据父级code查询银行/账户类型列表结束");
        return  new ResultModel().success(map);
    }

    @ApiOperation("发标查询")
    @PostMapping("toBid")
    public ResultModel toBid () throws BusinessException {
        log.info("进入根据父级code发标信息");
        Map<String, Object> map = new HashMap<>();
        //产品
        List<Dictionary> productList =  dictService.findList(DictConstant.PRODUCT_TYPE);
        //是否显示标的
        List<Dictionary> showList = dictService.findList(DictConstant.IS_SHOW);
        //标的类型
        List<Dictionary> tenderList = dictService.findList(DictConstant.TENDER_TYPE);
        //单人限额
        List<Dictionary> quotaList = dictService.findList(DictConstant.QUOTA);
        //期限
        List<Dictionary> deadlineList = dictService.findList(DictConstant.DEADLINE);
        //还款方式
        List<Dictionary> repaymentList = dictService.findList(DictConstant.REPAYMENT);
        map.put("product", productList);
        map.put("show", showList);
        map.put("tender", tenderList);
        map.put("quota", quotaList);
        map.put("deadline", deadlineList);
        map.put("repayment", repaymentList);
        log.info("进入根据父级code发标信息结束");
        return  new ResultModel().success(map);
    }

    @PostMapping("toFindContract")
    public ResultModel toFindContract () throws BusinessException {
        log.info("进入根据父级code查询借款合同类型");
        Map<String, Object> map = new HashMap<>();
        List<Dictionary> ContractList =  dictService.findList(DictConstant.TETONG);
        map.put("Contract", ContractList);
        log.info("进入根据父级code查询借款合同类型结束");
        return  new ResultModel().success(map);
    }
}

