package com.dj.mall.admin.vo.dictionary.dictionary;

import lombok.Data;

@Data
public class DictionaryVOReq {
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
}
