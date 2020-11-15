package com.dj.mall.product.pro.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.constant.CodeConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.QiNiuUtils;
import com.dj.mall.product.api.ProductApi;
import com.dj.mall.product.api.dto.ProductDTO;
import com.dj.mall.product.api.dto.ProductSkuDTO;
import com.dj.mall.product.pro.bo.ProductBO;
import com.dj.mall.product.pro.entity.ProductEntity;
import com.dj.mall.product.pro.entity.ProductSkuEntity;
import com.dj.mall.product.pro.mapper.ProductMapper;
import com.dj.mall.product.pro.service.productSkuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

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
        //七牛云
        QiNiuUtils.uploadByByte(productDTO.getImg(), productDTO.getProductImg());
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
        PageInfo.getRecords().forEach(pro -> {
            pro.setProductImg(QiNiuUtils.URL + pro.getProductImg());
        });
        return PageResult.pageInfo(PageInfo.getCurrent(), PageInfo.getPages(), DozerUtil.mapList(PageInfo.getRecords(), ProductDTO.class));
    }

    /**
     * 根据ID查找商品信息
     * @param id 商品ID
     * @return
     * @throws BusinessException
     */
    @Override
    public ProductDTO findListById(Integer id) throws BusinessException {
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        ProductEntity dictionaryEntity = super.getOne(wrapper);
        return DozerUtil.map(dictionaryEntity, ProductDTO.class);
    }

    /**
     * 修改商品
     * @param productDTO 商品信息
     * @throws BusinessException
     */
    @Override
    public void updateProduct(ProductDTO productDTO) throws BusinessException {
        //生成uuid
        String uuid=null;
        if(productDTO.getImg() != null) {
            uuid = UUID.randomUUID().toString().replace("-", "") +
                    productDTO.getProductImg().substring(productDTO.getProductImg().lastIndexOf("."));
            productDTO.setImgUrl(QiNiuUtils.URL + uuid);
        }
        //修改spu
        getBaseMapper().updateById(DozerUtil.map(productDTO,ProductEntity.class));
        //修改sku
        List<ProductSkuDTO> proSkuList = productDTO.getSkuList();
        productSkuService.updateBatchById(DozerUtil.mapList(proSkuList,ProductSkuEntity.class));
        //上传图片
        if(productDTO.getImg() != null) {
            QiNiuUtils.uploadByByte(productDTO.getImg(), uuid);
        }

    }
}
