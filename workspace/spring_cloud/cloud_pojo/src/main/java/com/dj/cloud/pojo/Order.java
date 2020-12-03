package com.dj.cloud.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("djmall_order_detail")
public class Order {
    private Integer id;
    private Date createTime;
}
