package com.dj.mall.dict.api.dictproattr;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.api.dto.dictproattr.DictProAttrDTO;
import com.dj.mall.dict.api.dto.sku.SkuDTO;

import java.util.List;

public interface SkuApi {
    /**
     * sku列表
     * @return
     * @throws BusinessException
     */
    List<SkuDTO> skuList() throws BusinessException;

    /**
     * sku属性、属性值
     * @param code
     * @return
     * @throws BusinessException
     */
    List<SkuDTO> skuLists(String code) throws BusinessException;

    /**
     * 新增属性，属性值
     * @param skuDTO
     * @throws BusinessException
     */
    void insertSku(SkuDTO skuDTO) throws BusinessException;

    /**
     * 商品sku属性展示
     * @return
     * @throws BusinessException
     */
    List<SkuDTO> findSku(String productType) throws BusinessException;
}
