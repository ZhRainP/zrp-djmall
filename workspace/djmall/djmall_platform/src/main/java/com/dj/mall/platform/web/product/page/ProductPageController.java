package com.dj.mall.platform.web.product.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.product.api.ProductApi;
import com.dj.mall.product.api.ProductSkuApi;
import com.dj.mall.product.api.dto.ProductDTO;
import com.dj.mall.product.api.dto.ProductSkuDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductPageController {
    @Reference
    private ProductApi productApi;
    @Reference
    private ProductSkuApi productSkuApi;
    /**
     * 去详情
     * @return
     */
    @RequestMapping("toProduct/{id}")
    public String toProduct (@PathVariable Integer id, ModelMap model) {
        ProductDTO productDTO = productApi.findListByProId(id);
        model.put("product", productDTO);
        List<ProductSkuDTO> productSkuDTOList = productSkuApi.findProductSkuById(productDTO.getId());
        model.put("sku", productSkuDTOList);
        return "mall/product_detail";
    }
}
