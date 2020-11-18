package com.dj.mall.autr.api.user;

import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.dto.user.UserTokenDTO;
import com.dj.mall.common.base.BusinessException;

import java.util.List;

public interface UserApi {

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws BusinessException
     */
    UserDto findUserNameAndPwd(String username, String password) throws BusinessException;

    /**
     * 用户列表
     * @return
     * @throws BusinessException
     */
    List<UserDto> findList(UserDto userDto) throws BusinessException;

    /**
     * 根据Id获取用户信息
     * @param id 用户IDBusinessException
     * @return
     * @throws BusinessException
     */
    UserDto findUserById(Integer id) throws BusinessException;

    /**
     * 权限修改
     * @param userDto
     * @throws BusinessException
     */
    void updateAuthorizes(UserDto userDto) throws BusinessException;

    /**
     * 注册
     * @param userDto 注册信息
     * @return
     * @throws BusinessException
     */
    boolean insertUser(UserDto userDto) throws BusinessException, Exception;

    /**
     * 根据名字查找盐
     * @param username 用户名
     * @return
     * @throws BusinessException
     */
    String findSalt(String username) throws BusinessException;

    /**
     * 删除用户
     * @param userDto
     * @throws BusinessException
     */
    void deleteUser(UserDto userDto) throws BusinessException;

    /**
     * 修改用户
     * @param map
     */
    void updateUser(UserDto map) throws BusinessException;

    /**
     * 激活-状态
     * @param id 用户Id
     * @throws BusinessException
     */
    void active(Integer id) throws BusinessException;

    /**
     * 重置密码
     * @param id 用户Id
     * @throws BusinessException
     */
    void resetPassword(Integer id, UserDto admin) throws Exception, BusinessException;

    /**
     * 重置后修改密码
     * @param userDto
     * @throws BusinessException
     */
    void updatePwd(UserDto userDto) throws BusinessException;

    /**
     * 查找用户手机号，验证
     * @param userDto
     * @return
     * @throws BusinessException
     */
    UserDto findUserByPhone(UserDto userDto) throws BusinessException;

    /**
     * 普通用户登陆
     * @param username 用户名
     * @param password 密码
     * @return
     */
    UserTokenDTO loginToken(String username, String password);
}
