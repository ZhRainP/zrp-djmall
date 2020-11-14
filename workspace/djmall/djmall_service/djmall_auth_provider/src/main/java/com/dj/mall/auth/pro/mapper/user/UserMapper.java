package com.dj.mall.auth.pro.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.auth.pro.bo.UserBo;
import com.dj.mall.auth.pro.entity.user.UserEntity;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserMapper  extends BaseMapper<UserEntity> {
    List<UserBo> userList(UserBo userBo) throws DataAccessException;
}
