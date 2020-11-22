package com.dj.mall.autr.api.dto.area;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaDTO implements Serializable {
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
