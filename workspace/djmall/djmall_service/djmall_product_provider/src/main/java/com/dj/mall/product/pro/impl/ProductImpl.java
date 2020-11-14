package com.dj.mall.product.pro.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.constant.CodeConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.product.api.ProductApi;
import com.dj.mall.product.api.dto.ProductDTO;
import com.dj.mall.product.pro.bo.ProductBO;
import com.dj.mall.product.pro.entity.ProductEntity;
import com.dj.mall.product.pro.entity.ProductSkuEntity;
import com.dj.mall.product.pro.mapper.ProductMapper;
import com.dj.mall.product.pro.service.productSkuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductApi {
    @Autowired
    private productSkuService productSkuService;

    /**
     * 新增商品
     * @param productDTO 商品信息
     * @throws BusinessException
     */
    @Override
    public void insertProduct(ProductDTO productDTO) throws BusinessException {
        //商品主类保存
        ProductEntity productEntity = DozerUtil.map(productDTO, ProductEntity.class);
        super.save(productEntity);
        //sku保存
        List<ProductSkuEntity> productSkuEntities = DozerUtil.mapList(productDTO.getSkuList(), ProductSkuEntity.class);
        productSkuEntities.forEach(sku -> {
            //商品ID
            sku.setProductId(productEntity.getId());
            //非默认项
            sku.setIsDefault(CodeConstant.NOT_DEFAULT);
            //默认上架
            sku.setSkuStatus(CodeConstant.STATUS_UP);
        });
        //第一个默认处理
        productSkuEntities.get(0).setIsDefault(CodeConstant.IS_DEFAULT);
        productSkuService.saveBatch(productSkuEntities);
    }

    /**
     * 商品列表
     * @param productDTO 商品信息
     * @return
     * @throws BusinessException
     */
    @Override
    public PageResult findList(ProductDTO productDTO) throws BusinessException {
        Page<ProductBO> page = new Page<>(productDTO.getPageNo(), productDTO.getPageSize());
        IPage<ProductBO> PageInfo = super.baseMapper.findList(page, DozerUtil.map(productDTO, ProductBO.class));
        return PageResult.pageInfo(PageInfo.getCurrent(), PageInfo.getPages(), DozerUtil.mapList(PageInfo.getRecords(), ProductDTO.class));
    }
}
