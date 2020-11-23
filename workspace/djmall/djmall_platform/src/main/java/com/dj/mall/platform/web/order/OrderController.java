package com.dj.mall.platform.web.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.order.api.dto.OrderDTO;
import com.dj.mall.platform.vo.order.OrderVOReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Reference
    private OrderApi orderApi;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 提交订单
     * @param orderVOReq 订单信息
     * @param TOKEN 用户token
     * @return
     * @throws Exception
     */
    @RequestMapping("insertOrder")
    public ResultModel insertOrder(OrderVOReq orderVOReq, String TOKEN) throws Exception {
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        orderVOReq.setBuyerId(userDto.getId());
        orderApi.insertOrder(DozerUtil.map(orderVOReq, OrderDTO.class));
        return new ResultModel().success();
    }
}
