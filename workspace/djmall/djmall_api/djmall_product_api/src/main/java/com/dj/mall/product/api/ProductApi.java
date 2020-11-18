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

    /**
     * 根据ID查询商品信息
     * @param id 商品ID
     * @return
     * @throws BusinessException
     */
    ProductDTO findListById(Integer id) throws BusinessException;

    /**
     * 修改商品
     * @param productDTO 商品信息
     * @throws BusinessException
     */
    void updateProduct(ProductDTO productDTO) throws BusinessException;

    /**
     * 全部商家商品
     * @param productDTO
     * @return
     * @throws BusinessException
     */
    PageResult allList(ProductDTO productDTO) throws BusinessException;

    /**
     * 根据ID查商品详情
     * @param id 商品ID
     * @return
     * @throws BusinessException
     */
    ProductDTO findListByProId(Integer id) throws BusinessException;
}
