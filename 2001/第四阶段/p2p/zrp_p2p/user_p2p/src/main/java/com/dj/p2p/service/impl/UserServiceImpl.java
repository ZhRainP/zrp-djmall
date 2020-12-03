package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.client.DictApi;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.PasswordSecurityUtil;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.constant.UserConstant;
import com.dj.p2p.mapper.UserMapper;
import com.dj.p2p.pojo.logincount.LoginCount;
import com.dj.p2p.pojo.openaccount.OpenAccount;
import com.dj.p2p.pojo.user.User;
import com.dj.p2p.service.LoginCountService;
import com.dj.p2p.service.OpenAccountService;
import com.dj.p2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sun.security.util.Password;
import sun.swing.AccumulativeRunnable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OpenAccountService openAccountService;

    @Autowired
    private LoginCountService loginCountService;

    /**
     * 用户登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return
     */
    @Override
    public ResultModel getUserByUsernameAndPassword(String phone, String password) throws BusinessException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User user = super.getOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(-1, "手机号不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new BusinessException(-1, "密码错误");
        }
        if (user.getIsLock().equals(UserConstant.YES_LOCK_CODE)) {
            throw new BusinessException(-3, "账户已锁定，请联系管理员处理");
        }
        LoginCount loginCount = new LoginCount();
        loginCount.setUserId(user.getId());
        loginCount.setLoginTime(LocalDateTime.now());
        loginCountService.save(loginCount);
        String token = UUID.randomUUID().toString().replace("-", "");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(CacheConstant.USER_TOKEN_ + token, user, 24 * 3600 * 1000, TimeUnit.SECONDS);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return new ResultModel().success(map);
    }

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel addUser(User user) throws BusinessException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        User phoneUser = super.getOne(queryWrapper);
        if (phoneUser != null) {
            throw new BusinessException(-1, "手机号重复");
        }
        user.setIsLock(UserConstant.NO_LOCK_CODE);
        user.setUserStatus(UserConstant.NOT_ACTIVATION_CODE);
        super.save(user);
        String token = UUID.randomUUID().toString().replace("-", "");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(CacheConstant.USER_TOKEN_ + token, user, 24 * 3600 * 1000, TimeUnit.SECONDS);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return new ResultModel().success(map);
    }

    /**
     * 新增公司用户
     *
     * @param user 用户信息
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel addCompanyUser(User user) throws BusinessException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        User phoneUser = super.getOne(queryWrapper);
        if (phoneUser != null) {
            throw new BusinessException(-1, "手机号重复");
        }
        user.setIsLock(UserConstant.NO_LOCK_CODE);
        user.setUserStatus(UserConstant.YES_HAVE_BANK_CODE);
        super.save(user);
        String token = UUID.randomUUID().toString().replace("-", "");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(CacheConstant.USER_TOKEN_ + token, user, 24 * 3600 * 1000, TimeUnit.SECONDS);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return new ResultModel().success(map);
    }

    /**
     * 用户实名认证
     *
     * @param realName 真实姓名
     * @param idCard   身份证号
     * @param token    用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean userCertification(String realName, String idCard, String token) throws BusinessException {
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id_card", idCard);
        User user1 = super.getOne(queryWrapper1);
        if (user1 != null) {
            throw new BusinessException(-1, "该身份证号已实名");
        }
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        QueryWrapper<OpenAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        OpenAccount account = openAccountService.getOne(queryWrapper);
        if (account != null) {
            throw new BusinessException(-7, "已实名认证");
        }
        OpenAccount openAccount = new OpenAccount()
                .setUserId(user.getId())
                .setRealName(realName)
                .setIdCard(idCard);
        openAccountService.save(openAccount);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("user_status", UserConstant.NO_HAVE_BANK_CODE);
        updateWrapper.eq("id", user.getId());
        super.update(updateWrapper);
        user.setUserStatus(UserConstant.NO_HAVE_BANK_CODE);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(CacheConstant.USER_TOKEN_ + token, user, 24 * 3600 * 1000, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 用户开户
     *
     * @param openAccount 开户信息
     * @param level       账户类型
     * @param token       用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel userOpenAccount(OpenAccount openAccount, String level, String token) throws BusinessException {
        QueryWrapper<OpenAccount> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("bank_number", openAccount.getBankNumber());
        OpenAccount openAccount1 = openAccountService.getOne(queryWrapper1);
        if (openAccount1 != null) {
            throw new BusinessException(-1, "该银行卡以绑定");
        }
        //第三方银行
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        QueryWrapper<OpenAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        OpenAccount account = openAccountService.getOne(queryWrapper);
        if (!StringUtils.isEmpty(account.getBankNumber())) {
            throw new BusinessException(-6, "已开户");
        }
        String code = PasswordSecurityUtil.generateRandom(18);
        UpdateWrapper<OpenAccount> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("bank_number", openAccount.getBankNumber());
        updateWrapper.set("bank_type", openAccount.getBankType());
        updateWrapper.set("bind_bank", UserConstant.YES_BIND_CODE);
        updateWrapper.set("id_card", openAccount.getIdCard());
        updateWrapper.set("real_name", openAccount.getRealName());
        updateWrapper.set("pay_password", openAccount.getPayPassword());
        updateWrapper.set("reserved_phone", openAccount.getReservedPhone());
        updateWrapper.eq("user_id", user.getId());
        updateWrapper.set("virtual_card", code);
        openAccountService.update(updateWrapper);
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", user.getId());
        userUpdateWrapper.set("user_status", UserConstant.YES_HAVE_BANK_CODE);
        userUpdateWrapper.set("level", level);
        super.update(userUpdateWrapper);
        user.setLevel(UserConstant.INVESTORS_CODE);
        user.setUserStatus(UserConstant.YES_HAVE_BANK_CODE);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(CacheConstant.USER_TOKEN_ + token, user, 24 * 3600 * 1000, TimeUnit.SECONDS);
        Map<String, Object> map = new HashMap<>();
        map.put("virtualAccount", code);
        return new ResultModel().success(map);
    }

    /**
     * 获取用户列表
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel getUserList() throws BusinessException {
        List<User> users = this.baseMapper.getUserList();
        Map<String, Object> map = new HashMap<>();
        map.put("userList", users);
        return new ResultModel().success(map);
    }

    /**
     * 修改锁定状态
     *
     * @param userId 用户ID
     * @param isLock 锁定状态
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean updateLockStatus(Integer userId, String isLock) throws BusinessException {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId);
        updateWrapper.set("is_lock", isLock);
        super.update(updateWrapper);
        return true;
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel getUserById(String token) throws BusinessException {
        User redisUser = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        User user = this.baseMapper.getUserById(redisUser.getId());
        OpenAccount openAccount = new OpenAccount();
        if (!StringUtils.isEmpty(user.getVirtualCard())) {
            openAccount.setVirtualCard(user.getVirtualCard().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        }
        openAccount.setRealName(user.getRealName().replace(user.getRealName().charAt(user.getRealName().length() - 1) + "", "*"));
        openAccount.setIdCard(user.getIdCard().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        openAccount.setPhone(user.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        if (!StringUtils.isEmpty(user.getBankNumber())) {
            openAccount.setBankNumber(user.getBankNumber().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        }
        return new ResultModel().success(openAccount);
    }

    /**
     * 返回首页页面信息
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel getHomePageMessage() throws BusinessException {
        User user = this.baseMapper.getHomePageMessage();
        Map<String, Object> map = new HashMap<>();
        map.put("registerNumber", user.getLoginCount());
        return new ResultModel().success(map);
    }

    /**
     * 提现信息
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public String withdrawMessage(String token) throws BusinessException {
        User redisUser = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        QueryWrapper<OpenAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", redisUser.getId());
        OpenAccount account = openAccountService.getOne(queryWrapper);
        return account.getBankNumber().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 获取支付密码
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    @Override
    public String getPayPassword(String token) throws BusinessException {
        User redisUser = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        QueryWrapper<OpenAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", redisUser.getId());
        OpenAccount account = openAccountService.getOne(queryWrapper);
        return account.getPayPassword();
    }

    /**
     * 返回借款人信息
     *
     * @return
     * @throws BusinessException
     */
    @Override
    public ResultModel getBorrower() throws BusinessException {

        List<OpenAccount> openAccounts = this.baseMapper.getBorrower();
        Map<String, Object> map = new HashMap<>();
        map.put("borrowerList", openAccounts);
        return new ResultModel().success(map);
    }
}
