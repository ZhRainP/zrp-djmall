package com.dj.mall.product.api;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.product.api.dto.ProductSkuDTO;

import java.util.List;

public interface ProductSkuApi {
    /**
     * 根据商品ID查找SKU列表
     * @param productId 商品ID
     * @return
     * @throws BusinessException
     */
    List<ProductSkuDTO> findProductSkuById(Integer productId) throws BusinessException;


    List<ProductSkuDTO> getSkuById(Integer skuId) throws BusinessException;
}
