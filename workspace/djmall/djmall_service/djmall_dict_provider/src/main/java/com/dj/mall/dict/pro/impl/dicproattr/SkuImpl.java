package com.dj.mall.dict.pro.impl.dicproattr;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictproattr.SkuApi;
import com.dj.mall.dict.api.dto.dictproattr.DictProAttrDTO;
import com.dj.mall.dict.api.dto.sku.SkuDTO;
import com.dj.mall.dict.pro.bo.DictProAttrBO;
import com.dj.mall.dict.pro.bo.SkuBO;
import com.dj.mall.dict.pro.entity.SkuEntity;
import com.dj.mall.dict.pro.mapper.dictproattr.SkuMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkuImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuApi {
    /**
     * sku列表
     * @return
     * @throws BusinessException
     */
    @Override
    public List<SkuDTO> skuList() throws BusinessException {
        List<SkuBO> skuList = super.baseMapper.skuList();
        return DozerUtil.mapList(skuList, SkuDTO.class);
    }

    /**
     * 关联属性的回显
     * @param code
     * @return
     * @throws BusinessException
     */
    @Override
    public List<SkuDTO> skuLists(String code) throws BusinessException {
        /*QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("product_type", code);*/
        return DozerUtil.mapList(super.baseMapper.skuList(), SkuDTO.class);
    }

    /**
     * 添加sku
     * @param skuDTO sku信息
     * @throws BusinessException
     */
    @Override
    public void insertSku(SkuDTO skuDTO) throws BusinessException {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("product_type", skuDTO.getProductType());
        super.remove(wrapper);
        
        List<SkuEntity> list = new ArrayList<>();
        for (Integer show : skuDTO.getIds()) {
            SkuEntity skuEntity = new SkuEntity();
            skuEntity.setProductType(skuDTO.getProductType());
            skuEntity.setAttrId(show);
            list.add(skuEntity);
        }
        super.saveBatch(list);
    }

    /**
     * 根据商品类型查找sku
     * @param productType 商品类型
     * @return
     * @throws BusinessException
     */
    @Override
    public List<SkuDTO> findSku(String productType) throws BusinessException {
        List<SkuBO> skuBOList = super.baseMapper.findSku(productType);
        return DozerUtil.mapList(skuBOList, SkuDTO.class);
    }
}
