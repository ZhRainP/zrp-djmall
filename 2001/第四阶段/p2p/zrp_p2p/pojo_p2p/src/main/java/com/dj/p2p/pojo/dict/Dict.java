package com.dj.p2p.pojo.dict;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典数据代码
     */
    private String code;

    /**
     * 字典数据名称
     */
    private String dictName;

    /**
     * 字典数据父级代码
     */
    private String parentCode;


}
