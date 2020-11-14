package com.dj.mall.auth.pro.impl.user;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.pro.bo.UserBo;
import com.dj.mall.auth.pro.entity.user.UserEntity;
import com.dj.mall.auth.pro.entity.user.UserRoleEntity;
import com.dj.mall.auth.pro.mapper.user.UserMapper;
import com.dj.mall.auth.pro.service.user.UserRoleService;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.user.UserApi;
import com.dj.mall.cmpt.api.EMailApi;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.constant.CodeConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.PasswordSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class UserApiImpl extends ServiceImpl<UserMapper, UserEntity> implements UserApi {
    @Autowired
    private UserRoleService userRoleService;
    @Reference
    private EMailApi eMailApi;
    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws BusinessException
     */
    @Override
    public UserDto findUserNameAndPwd(String username, String password) throws BusinessException {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserEntity user = super.getOne(queryWrapper);
        //用户名验证
        if (user == null) {
            throw new BusinessException("用户名不存在");
        }
        //密码验证
        if(!user.getPassword().equals(password)){
            throw new BusinessException("密码错误");
        }
        //用户状态验证
        if(user.getStatus().equals(CodeConstant.USER_STATUS_NOT_ACTIVE)){
            throw new BusinessException("还未激活，请登录邮箱激活再试");
        }
        if(user.getStatus().equals(CodeConstant.USER_STATUS_RESET_PWD)){
            throw new BusinessException(-5, "密码已被重置，请您修改");
        }

        QueryWrapper<UserRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getId());
        UserRoleEntity userRoleEntity = userRoleService.getOne(wrapper);
        if(userRoleEntity == null){
            throw new BusinessException("暂无角色， 请联系管理员");
        }
        //登陆用户权限
        UserDto userDto = DozerUtil.map(user, UserDto.class);
        userDto.setRoleId(userRoleEntity.getRoleId());
        return userDto;
    }

    /**
     * 用户列表
     * @return
     * @throws BusinessException
     */
    @Override
    public List<UserDto> findList(UserDto userDto) throws BusinessException {
        List<UserBo> userEntityList = super.baseMapper.userList(DozerUtil.map(userDto, UserBo.class));
        return DozerUtil.mapList(userEntityList, UserDto.class);
    }

    /**
     * 根据Id获取用户信息
     * @param id 用户ID
     * @return
     * @throws BusinessException
     */
    @Override
    public UserDto findUserById(Integer id) throws BusinessException {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        UserEntity user = super.getOne(queryWrapper);
        return DozerUtil.map(user, UserDto.class);
    }

    /**
     * 修改用户权限
     * @param userDto
     * @throws BusinessException
     */
    @Override
    public void updateAuthorizes(UserDto userDto) throws BusinessException {
        UpdateWrapper<UserRoleEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userDto.getUserId());
        wrapper.set("role_id", userDto.getRoleId());
        userRoleService.update(wrapper);
        super.update();
    }

    /**
     * 注册用户
     * @param userDto 用户信息
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean insertUser(UserDto userDto) throws Exception, BusinessException {
        //注册时间
        userDto.setStartTime(LocalDateTime.now());
        //新增用户
        UserEntity userEntity = DozerUtil.map(userDto, UserEntity.class);
        userEntity.setStatus(CodeConstant.USER_STATUS_ACTIVE);
        if(userDto.getLevel().equals(CodeConstant.MERCHANT_ROLE_ID)){
            userEntity.setStatus(CodeConstant.USER_STATUS_NOT_ACTIVE);
        }
        super.save(userEntity);
        //用户角色关联增加
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(userDto.getLevel());
        userRoleEntity.setUserId(userEntity.getId());
        userRoleService.save(userRoleEntity);
        //注册的用户是否为商户--发邮件
        if(userDto.getLevel().equals(CodeConstant.MERCHANT_ROLE_ID)){
            //发邮件
//            eMailApi.sendMailHTML(userDto.getMail(),
//                    "用户激活",
//                    "<a href = 'http://location:8081/admin/user/active/"+userEntity.getId()+"'>点我激活</a>");
        }
        return true;
    }

    /**
     * 根据用户姓名获取信息
     * @param username 用户名
     * @return
     * @throws BusinessException
     */
    @Override
    public String findSalt(String username) throws BusinessException {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        UserEntity user = super.getOne(wrapper);
        return user.getSalt();
    }

    /**
     * 用户删除
     * @param userDto
     * @throws BusinessException
     */
    @Override
    public void deleteUser(UserDto userDto) throws BusinessException {
        //删除roleResource表数据
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userDto.getUserId());
        userRoleService.remove(wrapper);
        //删除用户
        super.removeById(userDto.getId());
    }

    /**
     * 用户修改
     * @param userDto
     * @throws BusinessException
     */
    @Override
    public void updateUser(UserDto userDto) throws BusinessException {
        super.updateById(DozerUtil.map(userDto, UserEntity.class));
    }

    /**
     * 激活
     * @param id 用户Id
     * @throws BusinessException
     */
    @Override
    public void active(Integer id) throws BusinessException {
        UpdateWrapper<UserEntity> wrapper = new UpdateWrapper<>();
        wrapper.set("status", CodeConstant.USER_STATUS_ACTIVE);
        wrapper.eq("id", id);
        super.update(wrapper);
    }

    /**
     * 重置密码
     * @param id 用户Id
     * @param admin
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void resetPassword(Integer id, UserDto admin) throws Exception, BusinessException {
        //生成6位密码，发送邮件
        String random = PasswordSecurityUtil.generateRandom(6);
        String salt = PasswordSecurityUtil.generateSalt();
        String newPassword = PasswordSecurityUtil.enCode32(PasswordSecurityUtil.enCode32(random) + salt);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPassword(newPassword);
        userEntity.setSalt(salt);
        //修改状态
        userEntity.setStatus(CodeConstant.USER_STATUS_RESET_PWD);
        super.updateById(userEntity);
        //邮件通知
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String mailText = "尊敬的用户，您的密码被管理员" + admin.getNickName() + "，于"+ formatter.format(now) + ",修改为" + random;
        System.out.println(mailText);
    }

    /**
     * 重置后修改密码
     * @param userDto 用户信息
     * @throws BusinessException
     */
    @Override
    public void updatePwd(UserDto userDto) throws BusinessException {
        UpdateWrapper wrapper = new UpdateWrapper<>();
        wrapper.set("password", userDto.getPassword());
        wrapper.set("salt", userDto.getSalt());
        wrapper.set("status", CodeConstant.USER_STATUS_ACTIVE);
        wrapper.eq("username", userDto.getUsername());
        super.update(wrapper);
    }
}
