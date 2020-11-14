package com.dj.mall.dict.pro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("djmall_dict_freight")
public class FreightEntity {

    /**
     * 物流公司ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 物流公司名称
     */
    private String logisticsCompany;

    /**
     * 运费
     */
    private Double freight;
}
