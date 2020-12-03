package com.dj.p2p.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.p2p.pojo.openaccount.OpenAccount;
import com.dj.p2p.pojo.user.User;
import org.springframework.dao.DataAccessException;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ldm
 * @since 2020-11-26
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户列表
     *
     * @return
     * @throws DataAccessException
     */
    List<User> getUserList() throws DataAccessException;

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return
     * @throws DataAccessException
     */
    User getUserById(Integer id) throws DataAccessException;

    /**
     * 返回首页页面信息
     *
     * @return
     * @throws DataAccessException
     */
    User getHomePageMessage() throws DataAccessException;

    /**
     * 返回借款人信息
     *
     * @return
     * @throws DataAccessException
     */
    List<OpenAccount> getBorrower() throws DataAccessException;
}
