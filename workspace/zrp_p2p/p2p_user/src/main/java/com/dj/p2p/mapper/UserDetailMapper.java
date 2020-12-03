package com.dj.p2p.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.p2p.user.UserDetail;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserDetailMapper extends BaseMapper<UserDetail> {

    List<UserDetail> getSafeCenterList() throws DataAccessException;

    UserDetail getWithdrawalList(Integer id) throws DataAccessException;

    List<UserDetail> findAllBorrower() throws DataAccessException;

    UserDetail findBorrowerByUserId(Integer userId) throws DataAccessException;
}
