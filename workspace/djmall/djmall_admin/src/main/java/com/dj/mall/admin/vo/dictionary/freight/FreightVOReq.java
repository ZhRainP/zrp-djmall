package com.dj.mall.admin.vo.dictionary.freight;

import lombok.Data;

@Data
public class FreightVOReq {
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
