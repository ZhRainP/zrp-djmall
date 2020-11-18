package com.dj.mall.platform.web.user.page;

import com.dj.mall.common.util.PasswordSecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserPageController {
    /**
     * 用户去登陆页面
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin () {
        return "mall/login";
    }
    @RequestMapping("toAdd")

    /**
     * 去注册
     */
    public String toAdd (ModelMap model) throws Exception {
        model.put("salt", PasswordSecurityUtil.generateSalt());
        return "mall/add";
    }
}
