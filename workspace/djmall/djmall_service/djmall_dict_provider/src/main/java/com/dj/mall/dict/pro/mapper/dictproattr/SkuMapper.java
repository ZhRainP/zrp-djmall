package com.dj.mall.dict.pro.mapper.dictproattr;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.pro.bo.SkuBO;
import com.dj.mall.dict.pro.entity.SkuEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface SkuMapper extends BaseMapper<SkuEntity> {
    /**
     * sku列表
     * @return
     * @throws DataAccessException
     */
    List<SkuBO> skuList() throws DataAccessException;

    /**
     * 商品SKU列表
     * @return
     * @throws DataAccessException
     */
    List<SkuBO> findSku(String productType) throws DataAccessException;
}
