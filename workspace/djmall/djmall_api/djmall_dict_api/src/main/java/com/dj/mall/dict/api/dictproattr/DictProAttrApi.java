package com.dj.mall.dict.api.dictproattr;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.api.dto.dictionary.DictionaryDTO;
import com.dj.mall.dict.api.dto.dictproattr.DictProAttrDTO;

import java.util.List;

/**
 * 商品属性服务
 */
public interface DictProAttrApi {


    /**
     * 属性列表
     * @return
     * @throws BusinessException
     */
    List<DictProAttrDTO> attrList() throws BusinessException;

    /**
     * 添加属性
     * @param dictProAttrDTO 属性名信息
     * @throws BusinessException
     */
    void insertAttrName(DictProAttrDTO dictProAttrDTO) throws BusinessException;

    /**
     * 根据ID查找属性名
     * @param id 商品属性ID
     * @return
     * @throws BusinessException
     */
    DictProAttrDTO findAttrNameById(Integer id) throws BusinessException;
}
