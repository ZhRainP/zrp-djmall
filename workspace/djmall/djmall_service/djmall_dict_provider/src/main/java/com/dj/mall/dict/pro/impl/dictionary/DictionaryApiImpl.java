package com.dj.mall.dict.pro.impl.dictionary;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.constant.CacheConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.dictionary.DictionaryApi;
import com.dj.mall.dict.api.dto.dictionary.DictionaryDTO;
import com.dj.mall.dict.pro.entity.DictionaryEntity;
import com.dj.mall.dict.pro.mapper.dictionary.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class DictionaryApiImpl extends ServiceImpl<DictionaryMapper, DictionaryEntity> implements DictionaryApi {

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 字典列表查询
     * @return
     * @throws BusinessException
     */
    @Override
    public List<DictionaryDTO> findList() throws BusinessException {
        List<DictionaryEntity> dictionaryEntityList = super.list();
        return DozerUtil.mapList(dictionaryEntityList, DictionaryDTO.class);
    }

    /**
     * 根据code查找字典数据
     * @param code CODE
     * @return
     * @throws BusinessException
     */
    @Override
    public DictionaryDTO findDictByCode(String code) throws BusinessException {
        QueryWrapper<DictionaryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        DictionaryEntity dictionaryEntity = super.getOne(wrapper);
        return DozerUtil.map(dictionaryEntity, DictionaryDTO.class);
    }

    /**
     * 修改字典名称
     * @param dictionaryDTO 字典信息
     * @throws BusinessException
     */
    @Override
    public void updateDict(DictionaryDTO dictionaryDTO) throws BusinessException {
        UpdateWrapper<DictionaryEntity> wrapper = new UpdateWrapper<>();
        wrapper.set("dictionary_name", dictionaryDTO.getDictionaryName());
        wrapper.eq("code", dictionaryDTO.getCode());
        super.update(wrapper);
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(CacheConstant.DICT_ALL_KEY,
                dictionaryDTO.getSuperCode());
    }

    /**
     * 增加字典数据
     * @param dictionaryDTO 字典数据
     * @throws BusinessException
     */
    @Override
    public void insertDict(DictionaryDTO dictionaryDTO) throws BusinessException {
        super.save(DozerUtil.map(dictionaryDTO, DictionaryEntity.class));
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(CacheConstant.DICT_ALL_KEY,
                dictionaryDTO.getSuperCode());
    }

    /**
     * 根据父级code查列表
     * @param parentCode 父级code
     * @return
     * @throws BusinessException
     */
    @Override
    public List<DictionaryDTO> findDictByPCode(String parentCode) throws BusinessException {
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<DictionaryEntity> permissionList = (List<DictionaryEntity>) hashOperations.get(CacheConstant.DICT_ALL_KEY,
                parentCode);
        if(permissionList == null){
            QueryWrapper<DictionaryEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("super_code", parentCode);
            permissionList = super.list(wrapper);
            hashOperations.put(CacheConstant.DICT_ALL_KEY,
                    parentCode, permissionList);
        }
        return DozerUtil.mapList(permissionList, DictionaryDTO.class);
    }
}
