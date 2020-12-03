package com.dj.p2p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.user.UserDetail;

import java.util.List;

public interface UserDetailService extends IService<UserDetail> {
    void realName(UserDetail userDetail) throws BusinessException;

    UserDetail openCount(Integer userId) throws BusinessException;

    void insertOpenCount(UserDetail userDetail, String token) throws BusinessException;

    List<UserDetail> getSafeCenterList() throws BusinessException;

    UserDetail getUserDetailByUserId(Integer userId) throws BusinessException;

    UserDetail getWithdrawalList(Integer userId) throws BusinessException;

    List<UserDetail> findAllBorrower() throws BusinessException;

    UserDetail findBorrowerByUserId(Integer userId) throws BusinessException;
}
