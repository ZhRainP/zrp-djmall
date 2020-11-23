package com.dj.mall.order.pro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.order.pro.entity.OrderDetail;
import com.dj.mall.order.pro.mapper.OrderDetailMapper;
import com.dj.mall.order.pro.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
