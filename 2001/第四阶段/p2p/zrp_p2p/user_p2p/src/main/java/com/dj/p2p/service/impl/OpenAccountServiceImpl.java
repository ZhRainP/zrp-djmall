package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.mapper.OpenAccountMapper;
import com.dj.p2p.pojo.openaccount.OpenAccount;
import com.dj.p2p.pojo.user.User;
import com.dj.p2p.service.OpenAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ldm
 * @since 2020-11-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OpenAccountServiceImpl extends ServiceImpl<OpenAccountMapper, OpenAccount> implements OpenAccountService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 返回用户开户信息回显
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel returnUserOpenAccountMessage(String token) throws BusinessException {
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        QueryWrapper<OpenAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        OpenAccount openAccount = super.getOne(queryWrapper);
        return new ResultModel().success(openAccount);
    }
}
