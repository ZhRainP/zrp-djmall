package com.dj.p2p.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.openaccount.OpenAccount;
import com.dj.p2p.pojo.user.User;


public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return
     * @throws BusinessException
     */
    ResultModel getUserByUsernameAndPassword(String phone, String password) throws BusinessException;

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return
     * @throws BusinessException
     */
    ResultModel addUser(User user) throws BusinessException;

    /**
     * 新增公司用户
     *
     * @param user 用户信息
     * @return
     * @throws BusinessException
     */
    ResultModel addCompanyUser(User user) throws BusinessException;

    /**
     * 用户实名认证
     *
     * @param realName 真实姓名
     * @param idCard   身份证号
     * @param token    用户令牌
     * @return
     * @throws BusinessException
     */
    boolean userCertification(String realName, String idCard, String token) throws BusinessException;

    /**
     * 用户开户
     *
     * @param openAccount 开户信息
     * @param level       账户类型
     * @param token       用户令牌
     * @return
     * @throws BusinessException
     */
    ResultModel userOpenAccount(OpenAccount openAccount, String level, String token) throws BusinessException;

    /**
     * 获取用户列表
     *
     * @return
     * @throws BusinessException
     */
    ResultModel getUserList() throws BusinessException;

    /**
     * 修改锁定状态
     *
     * @param userId 用户ID
     * @param isLock 锁定状态
     * @return
     * @throws BusinessException
     */
    boolean updateLockStatus(Integer userId, String isLock) throws BusinessException;

    /**
     * 根据用户ID获取用户信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    ResultModel getUserById(String token) throws BusinessException;

    /**
     * 返回首页页面信息
     *
     * @return
     * @throws BusinessException
     */
    ResultModel getHomePageMessage() throws BusinessException;

    /**
     * 提现信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    String withdrawMessage(String token) throws BusinessException;

    /**
     * 获取支付密码
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    String getPayPassword(String token) throws BusinessException;

    /**
     * 返回借款人信息
     *
     * @return
     * @throws BusinessException
     */
    ResultModel getBorrower() throws BusinessException;
}
