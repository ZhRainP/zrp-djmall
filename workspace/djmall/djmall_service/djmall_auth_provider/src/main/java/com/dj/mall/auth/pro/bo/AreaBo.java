package com.dj.mall.auth.pro.bo;

import lombok.Data;

@Data
public class AreaBo {
    private Integer id;
    /**
     * 区域名
     */
    private String areaName;
    /**
     * 区域ID
     */
    private Integer areaParentId;

}
