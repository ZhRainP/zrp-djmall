package com.dj.mall.admin.web.dictionary.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dictionary.dictionary.DictionaryVOResp;
import com.dj.mall.common.constant.CodeConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictionary.DictionaryApi;
import com.dj.mall.dict.api.dto.dictionary.DictionaryDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("dict")
public class DictionaryPageController {
    @Reference
    private DictionaryApi dictionaryApi;

    /**
     * 去列表
     * @return
     */
    @RequestMapping("toList")
    public String toDictList () {
        return "dictionary/dictionary_list";
    }

    /**
     * 去修改， 根据code查找字典数据
     * @param code
     * @param model
     * @return
     */
    @RequestMapping("toUpdate")
    public String toUpdate (String code, ModelMap model) {
        DictionaryDTO dictionaryDTO = dictionaryApi.findDictByCode(code);
        model.put("dict", dictionaryDTO);
        return "dictionary/update_dict";
    }

    /**
     * 去添加
     * @param model
     * @return
     */
    @RequestMapping("toInsert")
    public String toInsert (ModelMap model) {
        List<DictionaryDTO> dictList = dictionaryApi.findDictByPCode(CodeConstant.PARENT_CODE);
        model.put("parents", DozerUtil.mapList(dictList, DictionaryVOResp.class));
        return "dictionary/insert_dict";
    }
}
