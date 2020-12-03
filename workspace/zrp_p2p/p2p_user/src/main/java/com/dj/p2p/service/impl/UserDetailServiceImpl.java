package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.PasswordSecurityUtil;
import com.dj.p2p.mapper.UserDetailMapper;
import com.dj.p2p.service.UserDetailService;
import com.dj.p2p.service.UserService;
import com.dj.p2p.service.UserWalletService;
import com.dj.p2p.user.UserDetail;
import com.dj.p2p.util.DictConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements UserDetailService {
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void realName(UserDetail userDetail) throws BusinessException {
        //默认未开户
        userDetail.setOpenStatus(DictConstant.BANK_NOT_OPEN_STATUS);
        //默认未认证
        userDetail.setApproveStatus(DictConstant.APPROVE_TYPE_1);
        if(userDetail.getOpenStatus().equals(DictConstant.BANK_OPEN_STATUS)){
            userDetail.setApproveStatus(DictConstant.APPROVE_TYPE);
        }
        super.save(userDetail);
    }

    @Override
    public UserDetail openCount(Integer userId) throws BusinessException {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        UserDetail user = super.getOne(wrapper);
        return user;
    }

    @Override
    public void insertOpenCount(UserDetail userDetail, String token) throws BusinessException {
        String virtualCard = PasswordSecurityUtil.generateRandom(16);
        UpdateWrapper wrapper = new UpdateWrapper<>();
        wrapper.set("id_card_type", DictConstant.IDCARD_TYPE);
        wrapper.set("bank_card", userDetail.getBankCard());
        wrapper.set("account_type", userDetail.getAccountType());
        wrapper.set("bank_phone", userDetail.getBankPhone());
        wrapper.set("transaction_pwd", userDetail.getTransactionPwd());
        wrapper.set("virtual_card", virtualCard);
        wrapper.set("open_status", DictConstant.BANK_OPEN_STATUS);
        wrapper.set("approve_status", DictConstant.APPROVE_TYPE);
        wrapper.set("balance", 0);
        wrapper.eq("user_id", userDetail.getUserId());
        super.update(wrapper);
        QueryWrapper<UserDetail> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id", userDetail.getUserId());
        UserDetail users = super.getOne(wrapper1);
        redisTemplate.delete(token);
        redisTemplate.opsForValue().set(token, users);
    }

    @Override
    public List<UserDetail> getSafeCenterList() throws BusinessException {
        List<UserDetail> safeCenterList = super.baseMapper.getSafeCenterList();
        return safeCenterList;
    }

    @Override
    public UserDetail getUserDetailByUserId(Integer userId) throws BusinessException {
        QueryWrapper<UserDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        UserDetail userDetail = super.getOne(wrapper);
        return userDetail;
    }

    @Override
    public UserDetail getWithdrawalList(Integer userId) throws BusinessException {
        return super.baseMapper.getWithdrawalList(userId);
    }

    @Override
    public List<UserDetail> findAllBorrower() throws BusinessException {
        return super.baseMapper.findAllBorrower();
    }

    @Override
    public UserDetail findBorrowerByUserId(Integer userId) throws BusinessException {
        UserDetail userDetail = super.baseMapper.findBorrowerByUserId(userId);
        return userDetail;
    }

}
