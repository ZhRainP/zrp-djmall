package com.dj.mall.dict.api.dictionary;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.api.dto.dictionary.DictionaryDTO;

import java.util.List;

/**
 * 字典服务
 */
public interface DictionaryApi {

    /**
     * 字典列表
     * @return
     * @throws BusinessException
     */
    List<DictionaryDTO> findList() throws BusinessException;

    /**
     * 根据code查找字典数据
     * @param code CODE
     * @return
     * @throws BusinessException
     */
    DictionaryDTO findDictByCode(String code) throws BusinessException;

    /**
     * 修改字典名字
     * @param dictionaryDTO 字典信息
     * @throws BusinessException
     */
    void updateDict(DictionaryDTO dictionaryDTO) throws BusinessException;

    /**
     * 添加字典数据
     * @param dictionaryDTO 字典数据
     * @throws BusinessException
     */
    void insertDict(DictionaryDTO dictionaryDTO) throws BusinessException;

    /**
     * 根据父级code查询列表
     * @param parentCode 父级code
     * @return
     * @throws BusinessException
     */
    List<DictionaryDTO> findDictByPCode(String parentCode) throws BusinessException;
}
