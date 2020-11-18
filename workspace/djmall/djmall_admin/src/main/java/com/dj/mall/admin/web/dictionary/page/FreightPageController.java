package com.dj.mall.admin.web.dictionary.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dictionary.dictionary.DictionaryVOResp;
import com.dj.mall.common.constant.CodeConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictionary.DictionaryApi;
import com.dj.mall.dict.api.dto.dictionary.DictionaryDTO;
import com.dj.mall.dict.api.dto.freight.FreightDTO;
import com.dj.mall.dict.api.freight.FreightApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("freight")
public class FreightPageController {
    @Reference
    private FreightApi freightApi;

    @Reference
    private DictionaryApi dictionaryApi;
    /**
     * 去运费列表
     * @return
     */
    @RequestMapping("toList")
    public String toList(ModelMap model) {
        List<DictionaryDTO> dictList = dictionaryApi.findDictByPCode("LOGIGSTICS_COMPANY");
        model.put("parents", DozerUtil.mapList(dictList, DictionaryVOResp.class));
        return "freight/freight_list";
    }

    /**
     * 去修改运费
     * @param id 运费id
     * @param model
     * @return
     */
    @RequestMapping("toUpdate")
    public String toUpdate(Integer id, ModelMap model) {
        FreightDTO freightDTO = freightApi.findFreight(id);
        model.put("freight", freightDTO);
        return "freight/update_freight";
    }
}
