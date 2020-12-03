package com.dj.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.cloud.config.ResultModel;
import com.dj.cloud.mapper.UserMapper;
import com.dj.cloud.pojo.User;
import com.dj.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public ResultModel<Object> findUsernameAndPassword(String password, String username) throws Exception {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        User users = super.getOne(wrapper);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String token = UUID.randomUUID().toString().replace("-", "");
        valueOperations.set("TOKEN_"+token, users);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return new ResultModel<>().success(map);
    }
}
