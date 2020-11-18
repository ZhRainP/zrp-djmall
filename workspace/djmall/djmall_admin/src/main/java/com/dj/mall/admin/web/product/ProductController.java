package com.dj.mall.admin.web.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.product.ProductSKUVOResp;
import com.dj.mall.admin.vo.product.ProductVOReq;
import com.dj.mall.admin.vo.product.ProductVOResp;
import com.dj.mall.admin.vo.sku.SkuResp;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictproattr.SkuApi;
import com.dj.mall.dict.api.dto.sku.SkuDTO;
import com.dj.mall.product.api.ProductApi;
import com.dj.mall.product.api.ProductSkuApi;
import com.dj.mall.product.api.dto.ProductDTO;
import com.dj.mall.product.api.dto.ProductSkuDTO;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class  ProductController {
    @Reference(timeout = 3000)
    private SkuApi skuApi;
    @Reference(timeout = 3000)
    private ProductApi productApi;
    @Reference(timeout = 3000)
    private ProductSkuApi productSkuApi;
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
    public ResultModel insertProduct (ProductVOReq productVOReq, MultipartFile img) throws Exception{
        String fileName = UUID.randomUUID().toString().replace("-", "")+
                img.getOriginalFilename().substring(img.getOriginalFilename().indexOf("."));
        productVOReq.setProductImg(fileName);
        ProductDTO productDTO = DozerUtil.map(productVOReq, ProductDTO.class);
        productDTO.setImage(img.getBytes());
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

    /**
     * 根据id查找商品sku
     * @param model
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping("productSkuList")
    public ResultModel productSkuList(ModelMap model, Integer productId) throws Exception {
        List<ProductSkuDTO> productSkuDTOList = productSkuApi.findProductSkuById(productId);
        model.put("proSku", productSkuDTOList);
        return new ResultModel().success(DozerUtil.mapList(productSkuDTOList, ProductSKUVOResp.class));
    }

    /**
     * 修改商品
     * @param productVOReq 商品信息
     * @param img 商品图片
     * @return
     * @throws Exception
     */
    @RequestMapping("updateProduct")
    public ResultModel<Object> updateProduct(ProductVOReq productVOReq, MultipartFile img) throws Exception{
        ProductDTO productDTO = DozerUtil.map(productVOReq, ProductDTO.class);
        if(img.getOriginalFilename() != null ){
            productVOReq.setImage(img.getBytes());
            productVOReq.setProductImg(img.getOriginalFilename());
        }
        //修改
        productApi.updateProduct(DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel<>().success();
    }
}