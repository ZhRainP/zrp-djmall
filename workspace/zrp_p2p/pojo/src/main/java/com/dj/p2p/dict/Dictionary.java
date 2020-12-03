package com.dj.p2p.dict;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("p2p_dict")
public class Dictionary {
    /**
     * CODE
     */
    private String code;
    /**
     * 数据名
     */
    private String baseName;
    /**
     * 上级CODE
     */
    private String pCode;

}
