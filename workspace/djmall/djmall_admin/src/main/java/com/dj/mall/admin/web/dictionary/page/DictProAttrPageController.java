package com.dj.mall.admin.web.dictionary.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.dict.api.dictproattr.DictProAttrApi;
import com.dj.mall.dict.api.dictproattr.SkuApi;
import com.dj.mall.dict.api.dto.dictproattr.DictProAttrDTO;
import com.dj.mall.dict.api.dto.sku.SkuDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("attr")
public class DictProAttrPageController {
    @Reference
    private DictProAttrApi dictProAttrApi;
    @Reference
    private SkuApi skuApi;

    /**
     * 去属性列表
     * @return
     */
    @RequestMapping("toList")
    public String toList () {
        return "dictproattr/attr_list";
    }

    /**
     * 根据ID查找属性名
     * @param id 商品属性ID
     * @param model
     * @return
     */
    @RequestMapping("toRelated")
    public String toRelated (Integer id, ModelMap model) {
        DictProAttrDTO dictProAttrDTO = dictProAttrApi.findAttrNameById(id);
        model.put("attr", dictProAttrDTO);
        return "dictproattr/related";
    }

    /**
     * 去sku列表
     * @return
     */
    @RequestMapping("toSkuList")
    public String toSkuList() {
        return "dictproattr/sku_list";
    }

    /**
     * 去关联属性
     * @param code 通过code查询
     * @param model
     * @return
     */
    @RequestMapping("toGeneralAssociated")
    public String toGeneralAssociated (String code, ModelMap model) {
        List<SkuDTO> skuDTOList = skuApi.skuLists(code);
        String ids = "";
        for (SkuDTO skuDTO : skuDTOList) {
            ids += skuDTO.getAttrId() + ",";
        }
        ids = ids.substring(0, ids.length()-1);
        model.put("ids", ids);
        model.put("code", code);
        return "dictproattr/relates";
    }
}
