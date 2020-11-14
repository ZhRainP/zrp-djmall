package com.dj.mall.product.api;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.product.api.dto.ProductDTO;

public interface ProductApi {
    /**
     * 添加商品
     * @param productDTO 商品信息
     * @throws BusinessException
     */
    void insertProduct(ProductDTO productDTO) throws BusinessException;

    /**
     * 商品展示列表
     * @param productDTO 商品信息
     * @return
     * @throws BusinessException
     */
    PageResult findList(ProductDTO productDTO) throws BusinessException;
}
