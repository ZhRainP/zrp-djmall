package com.dj.p2p.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.constant.RiskConstant;
import com.dj.p2p.mapper.DictMapper;
import com.dj.p2p.pojo.dict.Dict;

import com.dj.p2p.service.DictService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * 根据父级CODE获取字典数据
     *
     * @param parentCode 父级CODE
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Dict> selectDictByParentCode(String parentCode) throws BusinessException {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_code",parentCode);
        List<Dict> list = super.list(queryWrapper);
        return list;
    }

    /**
     * 返回字典信息
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnDictMessage() throws BusinessException {
        List<Dict> subjectProductList = selectDictByParentCode(RiskConstant.SUBJECT_PRODUCT_CODE);
        List<Dict> isLookSubjectList = selectDictByParentCode(RiskConstant.IS_LOOK_SUBJECT_CODE);
        List<Dict> subjectTypeList = selectDictByParentCode(RiskConstant.SUBJECT_TYPE_CODE);
        List<Dict> singleLimitList = selectDictByParentCode(RiskConstant.A_SINGLE_LIMIT_CODE);
        List<Dict> timeLimitList = selectDictByParentCode(RiskConstant.TIME_LIMIT_CODE);
        List<Dict> payBackMoneyTypeList = selectDictByParentCode(RiskConstant.PAY_BACK_MONEY_TYPE_CODE);
        Map<String, Object> map = new HashMap<>();
        map.put("subjectProductList",subjectProductList);
        map.put("isLookSubjectList",isLookSubjectList);
        map.put("subjectTypeList",subjectTypeList);
        map.put("singleLimitList",singleLimitList);
        map.put("timeLimitList",timeLimitList);
        map.put("payBackMoneyTypeList",payBackMoneyTypeList);
        return new ResultModel().success(map);
    }
}
