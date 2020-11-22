package com.dj.mall.autr.api.dto.address;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDTO implements Serializable {
    /**
     * 地址ID
     */
    private Integer addressId;
    /**
     * 收货人姓名
     */
    private String userName;
    /**
     * 地址
     */
    private String address;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 当前登陆人ID
     */
    private Integer userId;
    /**
     * 省
     */
    private String provice;
    /**
     * 县区
     */
    private String counties;
    /**
     * 市
     */
    private String city;
}
