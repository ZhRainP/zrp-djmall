package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.dict.Dictionary;
import com.dj.p2p.mapper.DictMapper;
import com.dj.p2p.service.DictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DictServiceImpl extends ServiceImpl<DictMapper, Dictionary> implements DictService {

    @Override
    public List<Dictionary> findList(String pCode) throws BusinessException {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("p_code", pCode);
        List<Dictionary> dictionaryList = super.list(wrapper);
        return dictionaryList;
    }
}
