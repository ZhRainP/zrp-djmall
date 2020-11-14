package com.dj.mall.dict.pro.mapper.dictproattr;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.dict.pro.bo.DictProAttrBO;
import com.dj.mall.dict.pro.entity.DictProAttrEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface DictProAttrMapper extends BaseMapper<DictProAttrEntity> {
    List<DictProAttrBO> proAttrList() throws DataAccessException;
}
