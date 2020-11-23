package com.dj.mall.order.pro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.order.pro.entity.OrderInfo;
import com.dj.mall.order.pro.mapper.OrderInfoMapper;
import com.dj.mall.order.pro.service.OrderInfoService;

@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
}
