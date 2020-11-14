package com.dj.mall.admin.web.user.page;


import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.autr.api.dto.role.RoleDTO;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.role.RoleApi;
import com.dj.mall.autr.api.user.UserApi;
import com.dj.mall.common.util.PasswordSecurityUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.dj.mall.common.constant.CodeConstant.USER_MANAGER_CODE;

@Controller
@RequestMapping("user")
public class UserPageController {

    @Reference
    private UserApi userApi;

    @Reference
    private RoleApi roleApi;

    /**
     * 去登陆
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin(){
        return "user/login";
    }

    /**
     * 去列表
     * @return
     */
    @RequestMapping("toList")
    @RequiresPermissions(USER_MANAGER_CODE)
    public String toList(){
        return "user/user_list";
    }

    /**
     * 去修改用户权限
     * @return
     */
    @RequestMapping("toAuthorize")
    public String toAuthorize(Integer id, ModelMap model){
        UserDto userDTO = userApi.findUserById(id);
        List<RoleDTO> roleDTOList = roleApi.findList();
        model.put("user", userDTO);
        model.put("roleDTOList", roleDTOList);
        return "user/user_authorize";
    }

    /**
     * 去注册
     * @return
     */
    @RequestMapping("toRegister")
    public String toRegister(ModelMap model) throws Exception{
        model.put("salt", PasswordSecurityUtil.generateSalt());
        return "user/register";
    }

    /**
     * 去修改
     * @param id 根据Id查找用户信息
     * @param model
     * @return
     */
    @RequestMapping("toUpdate")
    public String toUpdate (Integer id, ModelMap model){
        UserDto userDTO = userApi.findUserById(id);
        model.put("user", userDTO);
        return "user/UpdateUser";
    }

    /**
     * 激活
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("active")
    public String active (Integer id, ModelMap model){
        userApi.active(id);
        return "user/login";
    }

    /**
     * 去重置密码
     * @param username 用户名
     * @return
     */
    @RequestMapping("toResetPwd")
    public String toResetPwd(String username, ModelMap model) throws Exception {
        model.put("username", username);
        model.put("salt", PasswordSecurityUtil.generateSalt());
        return "user/reset_pwd";
    }
}
