package com.dj.mall.auth.pro.service.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.pro.entity.user.UserRoleEntity;
import com.dj.mall.auth.pro.mapper.user.UserRoleMapper;
import com.dj.mall.auth.pro.service.user.UserRoleService;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {
}
