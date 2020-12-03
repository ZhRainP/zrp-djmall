package com.dj.cloud.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("base_name")
public class Token {

	/** 令牌Id */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/** 用户Id */
	private Integer userId;

	/** 令牌号 */
	private String token;
}
