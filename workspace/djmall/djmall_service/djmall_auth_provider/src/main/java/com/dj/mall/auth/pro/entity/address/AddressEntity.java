package com.dj.mall.auth.pro.entity.address;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.dozer.Mapping;

@Data
@TableName("djmall_address")
public class AddressEntity {
    /**
     * 地址ID
     */
    @TableId(type = IdType.AUTO)
    @Mapping("addressId")
    private Integer id;
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
