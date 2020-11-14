package com.dj.mall.dict.pro.impl.freight;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dto.freight.FreightDTO;
import com.dj.mall.dict.api.freight.FreightApi;
import com.dj.mall.dict.pro.entity.FreightEntity;
import com.dj.mall.dict.pro.mapper.freight.FreightMapper;

import java.util.List;

@Service
public class FreightImpl extends ServiceImpl<FreightMapper, FreightEntity> implements FreightApi {
    /**
     * 运费列表展示
     * @return
     * @throws BusinessException
     */
    @Override
    public List<FreightDTO> freList() throws BusinessException {
        List<FreightEntity> freightEntityList = super.baseMapper.list();
        return DozerUtil.mapList(freightEntityList, FreightDTO.class);
    }

    /**
     * 根据ID获取运费信息
     * @param id 运费ID
     * @return
     * @throws BusinessException
     */
    @Override
    public FreightDTO findFreight(Integer id) throws BusinessException {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        FreightEntity freightEntity = super.getOne(wrapper);
        return DozerUtil.map(freightEntity, FreightDTO.class);
    }

    /**
     * 修改运费
     * @param freightDTO 运费信息
     * @throws BusinessException
     */
    @Override
    public void updateFreight(FreightDTO freightDTO) throws BusinessException {
        UpdateWrapper wrapper = new UpdateWrapper<>();
        wrapper.set("freight", freightDTO.getFreight());
        wrapper.eq("id", freightDTO.getId());
        super.update(wrapper);
    }

    @Override
    public void insert(FreightDTO freightDTO) throws BusinessException {
        super.save(DozerUtil.map(freightDTO, FreightEntity.class));
    }
}
