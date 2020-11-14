package com.dj.mall.admin.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.ProductVOReq;
import com.dj.mall.admin.vo.product.ProductVOResp;
import com.dj.mall.admin.vo.sku.SkuResp;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictproattr.SkuApi;
import com.dj.mall.dict.api.dto.sku.SkuDTO;
import com.dj.mall.product.api.ProductApi;
import com.dj.mall.product.api.dto.ProductDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Reference
    private SkuApi skuApi;
    @Reference(timeout = 3000)
    private ProductApi productApi;
    /**
     * 新增sku属性、属性值表
     * @return
     * @throws Exception
     */
    @RequestMapping("attrList")
    public ResultModel attrList (String productType) throws Exception{
        List<SkuDTO> skuDTOList = skuApi.findSku(productType);
        return new ResultModel().success(DozerUtil.mapList(skuDTOList, SkuResp.class));
    }

    /**
     * 添加商品
     * @param productVOReq 商品信息
     * @return
     * @throws Exception
     */
    @RequestMapping("insertProduct")
    public ResultModel insertProduct (ProductVOReq productVOReq) throws Exception{
        ProductDTO productDTO = DozerUtil.map(productVOReq, ProductDTO.class);
        productApi.insertProduct(productDTO);
        return new ResultModel().success();
    }

    /**
     * 商品展示
     * @param productVOReq 商品信息
     * @return
     * @throws Exception
     */
    @RequestMapping("productList")
    public ResultModel productList(ProductVOReq productVOReq) throws Exception {
        PageResult pageInfo = productApi.findList(DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel().success(PageResult.pageInfo(pageInfo.getCurrent(), pageInfo.getPages(),
                DozerUtil.mapList(pageInfo.getRecords(), ProductVOResp.class)));
    }
}