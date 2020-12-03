package com.dj.p2p.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.dict.Dictionary;

import java.util.List;

public interface DictService extends IService<Dictionary> {

    List<Dictionary> findList(String pCode) throws BusinessException;
}
