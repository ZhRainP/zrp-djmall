package com.dj.mall.dict.pro.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 字典实体类
 */
@Data
@TableName("dimall_bash_Dictionary")
public class DictionaryEntity {

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
