package com.dj.mall.autr.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ztree数据
 */
@Data
public class ZTreeData implements Serializable {
    /**  ID */
    private Integer id;

    /**  名称  */
    private String resourceName;

    /** 父级ID */
    private Integer parentId;

    /** 默认勾选状态 */
    private boolean checked = false;

    /** 展开 */
    private boolean open = true;
}
