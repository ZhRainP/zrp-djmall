package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.mapper.UserLoginMapper;
import com.dj.p2p.service.UserLoginService;
import com.dj.p2p.user.UserLogin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin> implements UserLoginService {

}
