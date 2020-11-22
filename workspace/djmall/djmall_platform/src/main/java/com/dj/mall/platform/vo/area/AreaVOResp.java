package com.dj.mall.platform.vo.area;

import lombok.Data;

@Data
public class AreaVOResp {

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
