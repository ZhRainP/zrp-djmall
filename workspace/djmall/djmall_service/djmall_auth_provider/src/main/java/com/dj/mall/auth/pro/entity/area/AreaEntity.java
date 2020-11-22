package com.dj.mall.auth.pro.entity.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("djmall_area")
public class AreaEntity {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 区域名
     */
    private String areaName;

    /**
     * 名字拼音
     */
    private String areaPinyin;

    /**
     * 区域ID
     */
    private Integer areaParentId;
}
