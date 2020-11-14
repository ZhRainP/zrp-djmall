package com.dj.mall.dict.pro.impl.dicproattr;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictproattr.AttrValueApi;
import com.dj.mall.dict.api.dto.attrvalue.AttrValueDTO;
import com.dj.mall.dict.pro.entity.AttrValueEntity;
import com.dj.mall.dict.pro.mapper.dictproattr.AttrValueMapper;

import java.util.List;

@Service
public class AttrValueImpl extends ServiceImpl<AttrValueMapper, AttrValueEntity> implements AttrValueApi {
    /**
     * 根据属性资源ID查找关联属性值
     * @param attrId 属性ID
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AttrValueDTO> attrValueList(Integer attrId) throws BusinessException {
        QueryWrapper<AttrValueEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id", attrId);
        List<AttrValueEntity> list = super.list(wrapper);
        return DozerUtil.mapList(list, AttrValueDTO.class);
    }

    /**
     * 添加属性值
     * @param attrValueDTO 属性值信息
     * @throws BusinessException
     */
    @Override
    public void insertAttrValue(AttrValueDTO attrValueDTO) throws BusinessException {
        AttrValueEntity attrValueEntity = new AttrValueEntity();
        attrValueEntity.setAttrId(attrValueDTO.getAttrId());
        attrValueEntity.setAttrValue(attrValueDTO.getAttrValue());
        super.save(attrValueEntity);
    }

    /**
     * 删除属性值
     * @param attrValueDTO 属性值信息
     * @throws BusinessException
     */
    @Override
    public void deleteArrt(AttrValueDTO attrValueDTO) throws BusinessException {
        super.removeById(attrValueDTO.getId());
    }

}
