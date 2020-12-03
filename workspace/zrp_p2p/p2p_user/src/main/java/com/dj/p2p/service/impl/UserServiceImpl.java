package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.mapper.UserMapper;
import com.dj.p2p.service.UserLoginService;
import com.dj.p2p.service.UserService;
import com.dj.p2p.user.User;
import com.dj.p2p.user.UserLogin;
import com.dj.p2p.util.DictConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserLoginService userLoginService;

    @Override
    public ResultModel findPhoneAndPassword(String phone, String password) throws BusinessException {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        wrapper.eq("password", password);
        User users = super.getOne(wrapper);
        if(users == null){
            throw new BusinessException("手机号不存在");
        }
        if(!users.getPassword().equals(password)){
            throw new BusinessException("密码错误");
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token = UUID.randomUUID().toString().replace("-", "");
        valueOperations.set(token, users);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        //保存登陆时间
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(users.getId());
        userLogin.setLoginTime(LocalDateTime.now());
        userLoginService.save(userLogin);
        return new ResultModel().success(map);
    }


    @Override
    public void registerAdmin(User user) throws BusinessException {
        user.setStatus(DictConstant.USER_NTO_LOCK);
        super.save(user);
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(user.getId());
        userLogin.setLoginTime(LocalDateTime.now());
        userLoginService.save(userLogin);
    }

    @Override
    public boolean findByPhone(String phone) throws BusinessException {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User phones = super.getOne(wrapper);
        if(phones != null){
            throw new BusinessException("该手机号已注册");
        }
        return false;
    }

    @Override
    public List<User> userInformation() throws BusinessException {
        List<User> userInformationList = super.baseMapper.userInformationList();
        return userInformationList;
    }

    @Override
    public void updateUserStatus(User user) throws BusinessException {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", user.getStatus());
        updateWrapper.eq("id",user.getId());
        super.update(updateWrapper);
    }



}
