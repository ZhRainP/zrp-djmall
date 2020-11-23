package com.dj.mall.auth.pro.impl.address;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.pro.entity.address.AddressEntity;
import com.dj.mall.auth.pro.mapper.AddressMapper;
import com.dj.mall.autr.api.address.AddressApi;
import com.dj.mall.autr.api.dto.address.AddressDTO;
import com.dj.mall.autr.api.dto.user.UserTokenDTO;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;

import java.util.List;

@Service
public class AddressApiImpl extends ServiceImpl<AddressMapper, AddressEntity> implements AddressApi {
    /**
     * 收货地址列表
     * @param id 用户ID
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AddressDTO> findAddressList(Integer id) throws BusinessException {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<AddressEntity> list = super.list(wrapper);
        return DozerUtil.mapList(list, AddressDTO.class);
    }

    /**
     * 添加收货地址
     * @param addressDTO
     * @throws BusinessException
     */
    @Override
    public void addAddress(AddressDTO addressDTO) throws BusinessException {
        super.save(DozerUtil.map(addressDTO, AddressEntity.class));
    }

    @Override
    public AddressDTO findAddress(Integer addressId) throws BusinessException {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("id", addressId);
        AddressEntity addressEntity = super.getOne(wrapper);
        return DozerUtil.map(addressEntity, AddressDTO.class);
    }
}
