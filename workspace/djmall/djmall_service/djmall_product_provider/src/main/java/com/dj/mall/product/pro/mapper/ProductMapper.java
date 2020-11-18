package com.dj.mall.product.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.product.pro.bo.ProductBO;
import com.dj.mall.product.pro.entity.ProductEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

public interface ProductMapper extends BaseMapper<ProductEntity> {
    /**
     * 商品列表
     * @param page 分页信息
     * @param productBO 商品信息
     * @return
     */
    IPage<ProductBO> findList(Page<ProductBO> page, @Param("productBO") ProductBO productBO) throws DataAccessException;

    /**
     * 用户看到的商品
     * @param page
     * @param productBO
     * @return
     */
    IPage<ProductBO> allList(Page<ProductBO> page, @Param("productBO")ProductBO productBO) throws DataAccessException;

    ProductBO findListByProId(Integer id) throws DataAccessException;
}
