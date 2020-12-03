package com.dj.p2p.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.user.UserWallet;


public interface UserWalletService extends IService<UserWallet> {
    UserWallet getUserWallet(Integer id) throws BusinessException;

    Boolean updateWallet(UserWallet userWallet) throws BusinessException;

    void insertBalance(Integer id) throws BusinessException;
}
