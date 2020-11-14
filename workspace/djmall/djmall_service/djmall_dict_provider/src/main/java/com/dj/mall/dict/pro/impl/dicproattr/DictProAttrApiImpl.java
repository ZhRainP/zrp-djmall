package com.dj.mall.dict.pro.impl.dicproattr;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictionary.DictionaryApi;
import com.dj.mall.dict.api.dictproattr.DictProAttrApi;
import com.dj.mall.dict.api.dto.dictproattr.DictProAttrDTO;
import com.dj.mall.dict.pro.bo.DictProAttrBO;
import com.dj.mall.dict.pro.entity.DictProAttrEntity;
import com.dj.mall.dict.pro.entity.DictionaryEntity;
import com.dj.mall.dict.pro.mapper.dictionary.DictionaryMapper;
import com.dj.mall.dict.pro.mapper.dictproattr.DictProAttrMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class DictProAttrApiImpl extends ServiceImpl<DictProAttrMapper, DictProAttrEntity> implements DictProAttrApi {


    /**
     * 商品属性列表
     * @return
     * @throws BusinessException
     */
    @Override
    public List<DictProAttrDTO> attrList() throws BusinessException {
        List<DictProAttrBO> attrList = super.baseMapper.proAttrList();
        return DozerUtil.mapList(attrList, DictProAttrDTO.class);
    }

    /**
     * 新增商品属性
     * @param dictProAttrDTO 属性名信息
     * @throws BusinessException
     */
    @Override
    public void insertAttrName(DictProAttrDTO dictProAttrDTO) throws BusinessException {
        super.save(DozerUtil.map(dictProAttrDTO, DictProAttrEntity.class));
    }

    /**
     * 根据ID查找属性名
     * @param id 商品属性ID
     * @return
     * @throws BusinessException
     */
    @Override
    public DictProAttrDTO findAttrNameById(Integer id) throws BusinessException {
        QueryWrapper<DictProAttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        DictProAttrEntity dictProAttrEntity = super.getOne(wrapper);
        return DozerUtil.map(dictProAttrEntity, DictProAttrDTO.class);
    }

}
