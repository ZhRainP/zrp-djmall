package com.dj.mall.dict.api.dto.dictionary;

import lombok.Data;

import java.io.Serializable;

@Data
public class DictionaryDTO implements Serializable {
    /**
     * code
     */
    private String code;

    /**
     * 字典名
     */
    private String dictionaryName;

    /**
     * 上级code
     */
    private String superCode;

    /**
     * 属性值
     */
    private String attrValue;
    /**
     * 属性名
     */
    private String attrName;
}
