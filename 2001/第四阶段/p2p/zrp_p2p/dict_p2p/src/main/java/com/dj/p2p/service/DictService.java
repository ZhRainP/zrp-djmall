package com.dj.p2p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.dict.Dict;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ldm
 * @since 2020-11-26
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据父级CODE获取字典数据
     * @param parentCode 父级CODE
     * @return
     * @throws BusinessException
     */
    List<Dict> selectDictByParentCode(String parentCode) throws BusinessException;

    /**
     * 返回字典信息
     * @return
     * @throws BusinessException
     */
    ResultModel returnDictMessage() throws BusinessException;
}
