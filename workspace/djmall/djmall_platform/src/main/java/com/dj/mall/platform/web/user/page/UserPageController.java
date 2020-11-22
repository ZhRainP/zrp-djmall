package com.dj.mall.platform.web.user.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.user.UserApi;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.platform.vo.UserTokenReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserPageController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Reference
    private UserApi userApi;

    /**
     * 用户去登陆页面
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin () {
        return "mall/login";
    }

    /**
     * 去注册
     */
    @RequestMapping("toAdd")
    public String toAdd (ModelMap model) throws Exception {
        model.put("salt", PasswordSecurityUtil.generateSalt());
        return "mall/add";
    }

    /**
     * 去个人中心
     * @return
     */
    @RequestMapping("toMyCenter")
    public String toMyCenter (String TOKEN, UserTokenReq userTokenReq, ModelMap model) {
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        userTokenReq.setUserId(userDto.getId());
        return "mall/my_center";
    }

    /**
     * 去收货地址列表
     * @return
     */
    @RequestMapping("toAddressList")
    public String toAddressList (String TOKEN, UserTokenReq userTokenReq) {
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        userTokenReq.setUserId(userDto.getId());
        return "mall/address_list";
    }

    /**
     * 去增加地址
     * @return
     */
    @RequestMapping("toAddress")
    public String toAddress (String TOKEN, UserTokenReq userTokenReq) {
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        userTokenReq.setUserId(userDto.getId());
        return "mall/address";
    }


    /**
     * 去个人信息管理
     * @param TOKEN ＴＯＫＥＮ
     * @param userTokenReq token信息
     * @return
     */
    @RequestMapping("toPim")
    public String toPim (String TOKEN, UserTokenReq userTokenReq, ModelMap model) {
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        userTokenReq.setUserId(userDto.getId());
        UserDto userDto1 = userApi.findUserById(userDto.getId());
        model.put("user", userDto1);
        return "mall/my_pim";
    }
}
 