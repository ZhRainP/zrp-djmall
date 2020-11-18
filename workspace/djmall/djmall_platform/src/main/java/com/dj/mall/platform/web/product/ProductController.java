package com.dj.mall.platform.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.platform.vo.product.ProductVOResp;
import com.dj.mall.product.api.ProductSkuApi;
import com.dj.mall.product.api.dto.ProductSkuDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Reference
    private ProductSkuApi productSkuApi;

    @RequestMapping("getSkuById")
    public ResultModel getSkuById (Integer skuId) throws Exception {
        List<ProductSkuDTO> productSkuDTOList = productSkuApi.getSkuById(skuId);
        return new ResultModel().success(DozerUtil.mapList(productSkuDTOList, ProductVOResp.class));
    }
}
