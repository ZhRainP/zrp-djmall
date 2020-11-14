package com.dj.mall.auth.pro.service.role.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.pro.entity.role.RoleResourceEntity;
import com.dj.mall.auth.pro.mapper.role.RoleResourceMapper;
import com.dj.mall.auth.pro.service.role.RoleResourceService;

@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResourceEntity> implements RoleResourceService {

}
