package com.dj.p2p.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.config.BusinessException;
import com.dj.p2p.config.ResultModel;
import com.dj.p2p.user.User;

import java.util.List;

public interface UserService extends IService<User> {

    ResultModel findPhoneAndPassword(String phone, String password) throws BusinessException;

    void registerAdmin(User user) throws BusinessException;

    boolean findByPhone(String phone) throws BusinessException;

    List<User> userInformation() throws BusinessException;

    void updateUserStatus(User user) throws BusinessException;
}
