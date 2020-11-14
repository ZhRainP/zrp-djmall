package com.dj.mall.dict.api.dto.freight;

import lombok.Data;

import java.io.Serializable;

@Data
public class FreightDTO implements Serializable {
    /**
     * 物流公司ID
     */
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
