package com.dj.mall.admin.web.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.resource.ResourceVOResp;
import com.dj.mall.admin.vo.user.UserVoReq;
import com.dj.mall.admin.vo.user.UserVoResp;
import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.resouce.ResourceApi;
import com.dj.mall.autr.api.user.UserApi;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.CacheConstant;
import com.dj.mall.common.constant.CodeConstant;
import com.dj.mall.common.util.DozerUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserApi userApi;

    @Reference
    private ResourceApi resourceApi;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @param session session
     * @return
     * @throws Exception
     */
    @PostMapping("login")
    public ResultModel<Object> login(String username, String password, HttpSession session) throws Exception {
        Assert.hasText(username, "用户名或密码不能为空");
        Assert.hasText(password, "用户名或密码不能为空");
        //登陆
        UserDto userDto = userApi.findUserNameAndPwd(username, password);
        //存session
        List<ResourceDTO> resourceList = resourceApi.findAllList(userDto.getId());
        userDto.setPermissionList(resourceList);
        session.setAttribute(CodeConstant.USER_SESSION,userDto );
        //得到主体
        Subject subject = SecurityUtils.getSubject();
        //请求securityManager的认证器
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        return new ResultModel().success();
    }

    /**
     * 用户列表
     * @return
     * @throws Exception
     */
    @GetMapping("list")
    public ResultModel list(UserVoReq userVoReq) throws Exception{
        List<UserDto> userList = userApi.findList(DozerUtil.map(userVoReq, UserDto.class));
        return new ResultModel().success(DozerUtil.mapList(userList, UserVoResp.class));
    }

    /**
     * 授权
     * @param userVoResp
     * @return
     * @throws Exception
     */
    @PostMapping("authorizes")
    public ResultModel authorizes (UserVoResp userVoResp) throws Exception {
        userApi.updateAuthorizes(DozerUtil.map(userVoResp, UserDto.class));
        return new ResultModel().success();
    }

    /**
     * 菜单列表
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("menuList")
    public ResultModel menuList(HttpSession session) throws Exception {
        UserDto user = (UserDto) session.getAttribute(CodeConstant.USER_SESSION);
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<ResourceDTO> permissionList = (List<ResourceDTO>) hashOperations.get(CacheConstant.ROLE_ALL_KEY,
                CacheConstant.RILE_ID_PREFIX + user.getRoleId());
        List<ResourceDTO> menuList = permissionList.stream()
                .filter(resourceDTO -> resourceDTO.getResourceType().equals(1)).collect(Collectors.toList());
        return new ResultModel().success(DozerUtil.mapList(menuList, ResourceVOResp.class));
    }

    /**
     * 注册
     * @param userVoReq
     * @return
     * @throws Exception
     */
    @PostMapping("register")
    public ResultModel register (UserVoReq userVoReq) throws Exception {
        Assert.hasText(userVoReq.getUsername(), "请输入账号");
        Assert.hasText(userVoReq.getPassword(), "密码不能为空");
        Assert.state(userVoReq.getPassword().equals(userVoReq.getConfirmPassword()), "密码不一致");
        Assert.hasText(userVoReq.getNickName(), "昵称不能为空");
        Assert.hasText(userVoReq.getMail(), "邮箱不能为空");
        Assert.hasText(userVoReq.getPhone(), "手机号不能为空");
        UserDto userDto = DozerUtil.map(userVoReq, UserDto.class);
        userApi.insertUser(userDto);
        return new ResultModel().success();
    }

    /**
     * 根据名字查找盐
     * @param username
     * @return
     * @throws Exception
     */
    @PostMapping("findSalt")
    public ResultModel findSalt(String username) throws Exception {
        String salt = userApi.findSalt(username);
        return new ResultModel().success(salt);
    }

    /**
     * 删除用户
     * @param userVoReq
     * @return
     * @throws Exception
     */
    @RequestMapping("del")
    public ResultModel deleteUser(UserVoReq userVoReq) throws Exception {
        userApi.deleteUser(DozerUtil.map(userVoReq, UserDto.class));
        return new ResultModel().success();
    }

    /**
     * 修改用户
     * @param userVoReq 返回userVoReq
     * @return
     * @throws Exception
     */
    @RequestMapping("updateUser")
    public ResultModel updateUser(UserVoReq userVoReq) throws Exception {
        userApi.updateUser(DozerUtil.map(userVoReq, UserDto.class));
        return new ResultModel().success();
    }

    /**
     * 重置密码
     * @param id 用户ID
     * @param Session 用户session
     * @return
     * @throws Exception
     */
    @RequestMapping("resetPassword")
    public ResultModel resetPassword (Integer id, HttpSession Session) throws Exception {
        UserDto admin = (UserDto) Session.getAttribute(CodeConstant.USER_SESSION);
        userApi.resetPassword(id, admin);
        return new ResultModel().success();
    }

    /**
     * 重置密码后修改密码
     * @param userVoReq
     * @return
     * @throws Exception
     */
    @RequestMapping("updatePwd")
    public ResultModel updatePwd (UserVoReq userVoReq) throws Exception{
        Assert.hasText(userVoReq.getPassword(), "密码不能为空");
        Assert.hasText(userVoReq.getConfirmPassword(), "确认密码不能为空");
        Assert.state(userVoReq.getPassword().equals(userVoReq.getConfirmPassword()), "密码不一致");
        userApi.updatePwd(DozerUtil.map(userVoReq, UserDto.class));
        return new ResultModel().success();
    }
}

