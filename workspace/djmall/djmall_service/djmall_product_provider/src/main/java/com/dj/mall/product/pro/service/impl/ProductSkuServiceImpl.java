package com.dj.mall.product.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.product.pro.entity.ProductSkuEntity;
import com.dj.mall.product.pro.mapper.ProductSkuServiceMapper;
import com.dj.mall.product.pro.service.productSkuService;
import org.springframework.stereotype.Service;

@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuServiceMapper, ProductSkuEntity> implements productSkuService {
}
