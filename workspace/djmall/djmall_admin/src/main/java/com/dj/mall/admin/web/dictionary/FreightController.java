package com.dj.mall.admin.web.dictionary;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dictionary.freight.FreightVOReq;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dto.freight.FreightDTO;
import com.dj.mall.dict.api.freight.FreightApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("freight")
public class FreightController {
    @Reference
    private FreightApi freightApi;

    /**
     * 运费列表展示
     * @return
     */
    @RequestMapping("freList")
    public ResultModel freList () throws Exception {
        List<FreightDTO> freightDTOList = freightApi.freList();
        return new ResultModel().success(DozerUtil.mapList(freightDTOList, FreightDTO.class));
    }

    /**
     * 修改运费
     * @param freightVOReq 运费信息
     * @return
     * @throws Exception
     */
    @RequestMapping("updateFreight")
    public ResultModel updateFreight (FreightVOReq freightVOReq) throws Exception {
        freightApi.updateFreight(DozerUtil.map(freightVOReq, FreightDTO.class));
        return new ResultModel().success();
    }

    /**
     * 添加运费
     * @param freightVOReq 运费信息
     * @return
     * @throws Exception
     */
    @RequestMapping("insert")
    public ResultModel insert (FreightVOReq freightVOReq) throws Exception {
        freightApi.insert(DozerUtil.map(freightVOReq, FreightDTO.class));
        return new ResultModel().success();
    }
}
