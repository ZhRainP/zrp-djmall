package com.dj.mall.admin.web.product.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.dict.api.dictionary.DictionaryApi;
import com.dj.mall.dict.api.dto.dictionary.DictionaryDTO;
import com.dj.mall.dict.api.dto.freight.FreightDTO;
import com.dj.mall.dict.api.freight.FreightApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductPageController {
    @Reference(timeout = 3000)
    private DictionaryApi dictionaryApi;
    @Reference(timeout = 3000)
    private FreightApi freightApi;

    /**
     * 去列表
     * @return
     */
    @RequestMapping("toList")
    public String toList() {
        return "product/product_list";
    }

    /**
     * 去商品新增
     * @param model
     * @return
     */
    @RequestMapping("toInsert")
    public String toInsert (ModelMap model) {
        List<DictionaryDTO> productType = dictionaryApi.findDictByPCode("PRODUCT_TYPE");
        model.put("productType", productType);
        List<FreightDTO> freightDTOList = freightApi.freList();
        model.put("freight", freightDTOList);
        return "product/insert_product";
    }

    public String toUpdate (Integer id, ModelMap model){

        return "product/update_product";
    }
}
