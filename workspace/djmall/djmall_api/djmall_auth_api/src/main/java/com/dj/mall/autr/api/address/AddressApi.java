package com.dj.mall.autr.api.address;

import com.dj.mall.autr.api.dto.address.AddressDTO;
import com.dj.mall.autr.api.dto.user.UserTokenDTO;
import com.dj.mall.common.base.BusinessException;

import java.util.List;

public interface AddressApi {
    /**
     * 收货地址列表
     * @param id 用户ID
     * @return
     * @throws BusinessException
     */
    List<AddressDTO> findAddressList(Integer id) throws BusinessException;

    /**
     * 添加收货地址
     * @param addressDTO
     * @throws BusinessException
     */
    void addAddress(AddressDTO addressDTO) throws BusinessException;

    /**
     * 根据地址ID查询地址
     * @param addressId 地址ID
     * @return
     * @throws BusinessException
     */
    AddressDTO findAddress(Integer addressId) throws BusinessException;
}
