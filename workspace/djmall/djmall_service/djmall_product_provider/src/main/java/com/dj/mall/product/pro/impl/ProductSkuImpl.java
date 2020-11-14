package com.dj.mall.product.pro.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.ProductSkuApi;
import com.dj.mall.product.api.dto.ProductSkuDTO;
import com.dj.mall.product.pro.entity.ProductSkuEntity;
import com.dj.mall.product.pro.mapper.ProductSkuMapper;

import java.util.List;
@Service
public class ProductSkuImpl extends ServiceImpl<ProductSkuMapper, ProductSkuEntity> implements ProductSkuApi {
    @Override
    public List<ProductSkuDTO> findProductSkuById(Integer id) throws BusinessException {
        QueryWrapper<ProductSkuEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", id);
        List<ProductSkuEntity> productSkuEntityList = super.list(wrapper);
        return DozerUtil.mapList(productSkuEntityList, ProductSkuDTO.class);
    }
}
