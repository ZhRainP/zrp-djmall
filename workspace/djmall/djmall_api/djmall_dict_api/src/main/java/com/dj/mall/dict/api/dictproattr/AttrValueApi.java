package com.dj.mall.dict.api.dictproattr;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.api.dto.attrvalue.AttrValueDTO;

import java.util.List;

public interface AttrValueApi {
    /**
     * 根据属性资源ID查找关联属性值
     * @param attrId 属性ID
     * @return
     * @throws Exception
     */
    List<AttrValueDTO> attrValueList(Integer attrId) throws BusinessException;

    /**
     * 添加属性值
     * @param attrValueDTO 属性值信息
     * @throws BusinessException
     */
    void insertAttrValue(AttrValueDTO attrValueDTO) throws BusinessException;

    /**
     * 删除属性值
     * @param attrValueDTO 属性值信息
     * @throws BusinessException
     */
    void deleteArrt(AttrValueDTO attrValueDTO) throws BusinessException;
}
