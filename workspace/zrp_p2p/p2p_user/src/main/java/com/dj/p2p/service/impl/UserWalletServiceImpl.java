package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.mapper.UserWalletMapper;
import com.dj.p2p.service.UserWalletService;
import com.dj.p2p.user.UserWallet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService {

    @Override
    public UserWallet getUserWallet(Integer userId) throws BusinessException {
        QueryWrapper<UserWallet> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        UserWallet userWallet = super.getOne(wrapper);
        return userWallet;
    }

    @Override
    public Boolean updateWallet(UserWallet userWallet) throws BusinessException {
        return updateById(userWallet);
    }

    @Override
    public void insertBalance(Integer id) throws BusinessException {
        UserWallet userWallet = new UserWallet();
        userWallet.setBalance(new BigDecimal(0));
        userWallet.setUserId(id);
        super.save(userWallet);
    }
}
