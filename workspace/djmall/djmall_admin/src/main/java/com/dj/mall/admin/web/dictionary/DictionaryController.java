package com.dj.mall.admin.web.dictionary;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dictionary.dictionary.DictionaryVOReq;
import com.dj.mall.admin.vo.dictionary.dictionary.DictionaryVOResp;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictionary.DictionaryApi;
import com.dj.mall.dict.api.dto.dictionary.DictionaryDTO;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dict")
public class DictionaryController {
    @Reference
    private DictionaryApi dictionaryApi;

    /**
     * 字典列表
     * @return
     * @throws Exception
     */
    @GetMapping("dictList")
    public ResultModel dictList() throws Exception{
        List<DictionaryDTO> dictList =  dictionaryApi.findList();
        return new ResultModel().success(DozerUtil.mapList(dictList, DictionaryVOResp.class));
    }

    /**
     * 修改字典名字
     * @param dictionaryVOReq 字典信息
     * @return
     * @throws Exception
     */
    @PostMapping("updateDict")
    public ResultModel updateDict(DictionaryVOReq dictionaryVOReq) throws Exception{
        dictionaryApi.updateDict(DozerUtil.map(dictionaryVOReq, DictionaryDTO.class));
        return new ResultModel().success();
    }

    /**
     * 添加字典数据
     * @param dictionaryVOReq 字典数据
     * @return
     * @throws Exception
     */
    @PostMapping("insertDict")
    public ResultModel insertDict (DictionaryVOReq dictionaryVOReq) throws Exception {
        Assert.hasText(dictionaryVOReq.getCode(), "code不能为空");
        Assert.hasText(dictionaryVOReq.getDictionaryName(), "字典名不能为空");
        DictionaryDTO dictionaryDTO = DozerUtil.map(dictionaryVOReq, DictionaryDTO.class);
        dictionaryApi.insertDict(dictionaryDTO);
        return new ResultModel().success();
    }

    /**
     * 根据父级CODE查找信息
     * @param superCode　父级code
     * @return
     * @throws Exception
     */
    @PostMapping("{superCode}/getChild")
    public ResultModel getChild (@PathVariable String superCode) throws Exception {
        List<DictionaryDTO> dictByPCode = dictionaryApi.findDictByPCode(superCode);
        return new ResultModel().success(DozerUtil.mapList(dictByPCode, DictionaryVOResp.class));
    }

}
