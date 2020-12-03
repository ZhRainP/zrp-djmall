package com.dj.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.cloud.config.ResultModel;
import com.dj.cloud.pojo.User;


public interface UserService extends IService<User> {

    ResultModel<Object> findUsernameAndPassword(String username, String password) throws Exception;
}
