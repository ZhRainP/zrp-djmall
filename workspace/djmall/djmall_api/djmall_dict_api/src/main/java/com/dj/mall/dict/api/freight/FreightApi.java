package com.dj.mall.dict.api.freight;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.api.dto.freight.FreightDTO;

import java.util.List;

public interface FreightApi {
    /**
     * 运费列表展示
     * @return
     * @throws BusinessException
     */
    List<FreightDTO> freList() throws BusinessException;

    /**
     * 根据Id获取运费信息
     * @param id 运费ID
     * @return
     * @throws BusinessException
     */
    FreightDTO findFreight(Integer id) throws BusinessException;

    /**
     * 修改运费
     * @param freightDTO
     * @throws BusinessException
     */
    void updateFreight(FreightDTO freightDTO) throws BusinessException;

    /**
     * 运费信息
     * @param freightDTO
     * @throws BusinessException
     */
    void insert(FreightDTO freightDTO) throws BusinessException;
}
