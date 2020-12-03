package com.dj.p2p.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.p2p.user.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> userInformationList() throws DataAccessException;
}
