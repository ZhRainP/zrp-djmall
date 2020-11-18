package com.dj.mall.admin.web.dictionary;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dictionary.attrvalue.AttrValueVOReq;
import com.dj.mall.admin.vo.dictionary.dictProattr.DictProAttrVOReq;
import com.dj.mall.admin.vo.dictionary.dictionary.DictionaryVOResp;
import com.dj.mall.admin.vo.sku.SkuVOReq;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictproattr.AttrValueApi;
import com.dj.mall.dict.api.dictproattr.DictProAttrApi;
import com.dj.mall.dict.api.dictproattr.SkuApi;
import com.dj.mall.dict.api.dto.attrvalue.AttrValueDTO;
import com.dj.mall.dict.api.dto.dictproattr.DictProAttrDTO;
import com.dj.mall.dict.api.dto.sku.SkuDTO;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("attr")
public class DictProAttrController {
    @Reference
    private DictProAttrApi dictProAttrApi;

    @Reference
    private AttrValueApi attrValueApi;

    @Reference
    private SkuApi skuApi;

    /**
     * 商品属性列表
     * @return
     * @throws Exception
     */
    @RequestMapping("attrList")
    public ResultModel attrList() throws Exception {
        List<DictProAttrDTO> attrList = dictProAttrApi.attrList();
        return new ResultModel().success(DozerUtil.mapList(attrList, DictProAttrDTO.class));
    }

    /**
     * 新增商品属性
     * @param dictProAttrVOReq 商品属性信息
     * @return
     * @throws Exception
     */
    @RequestMapping("insert")
    public ResultModel insert (DictProAttrVOReq dictProAttrVOReq) throws Exception {
        Assert.hasText(dictProAttrVOReq.getAttrName(), "属性名不能为空");
        DictProAttrDTO dictProAttrDTO = DozerUtil.map(dictProAttrVOReq, DictProAttrDTO.class);
        dictProAttrApi.insertAttrName(dictProAttrDTO);
        return new ResultModel().success();
    }

    /**
     * 根据属性资源ID查找关联属性值
     * @param attrId 属性ID
     * @return
     * @throws Exception
     */
    @RequestMapping("attrValue")
    public ResultModel attrValue (Integer attrId) throws Exception{
        List<AttrValueDTO> attrList = attrValueApi.attrValueList(attrId);
        return new ResultModel().success(DozerUtil.mapList(attrList, AttrValueDTO.class));
    }

    /**
     * 添加属性值
     * @param attrValueVOReq 属性值信息
     * @return
     * @throws Exception
     */
    @RequestMapping("insertAttrValue")
    public ResultModel insertAttrValue (AttrValueVOReq attrValueVOReq) throws Exception {
        Assert.hasText(attrValueVOReq.getAttrValue(), "属性值不能为空");
        AttrValueDTO attrValueDTO = DozerUtil.map(attrValueVOReq, AttrValueDTO.class);
        attrValueApi.insertAttrValue(attrValueDTO);
        return new ResultModel().success();
    }

    /**
     * 删除属性值
     * @param attrValueVOReq 属性值信息
     * @return
     * @throws Exception
     */
    @RequestMapping("deleteArrt")
    public ResultModel deleteArrt (AttrValueVOReq attrValueVOReq) throws Exception {
        attrValueApi.deleteArrt(DozerUtil.map(attrValueVOReq, AttrValueDTO.class));
        return new ResultModel().success();
    }

    /**
     * sku列表
     * @return
     * @throws Exception
     */
    @RequestMapping("skuList")
    public ResultModel skuList() throws Exception {
        List<SkuDTO> skuList = skuApi.skuList();
        return new ResultModel().success(DozerUtil.mapList(skuList, SkuDTO.class));
    }

    /**
     * 添加sku
     * @param skuVOReq sku信息
     * @return
     * @throws Exception
     */
    @RequestMapping("insertSku")
    public ResultModel insertSku (SkuVOReq skuVOReq)  throws Exception{
        SkuDTO skuDTO = DozerUtil.map(skuVOReq, SkuDTO.class);
        skuApi.insertSku(skuDTO);
        return new ResultModel().success();
    }
}
