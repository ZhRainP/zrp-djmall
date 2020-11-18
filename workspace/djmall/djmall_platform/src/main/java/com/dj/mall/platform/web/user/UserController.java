package com.dj.mall.platform.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.dto.user.UserTokenDTO;
import com.dj.mall.autr.api.user.UserApi;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.platform.vo.UserTokenVOResp;
import com.dj.mall.platform.vo.product.ProductVOReq;
import com.dj.mall.platform.vo.product.ProductVOResp;
import com.dj.mall.platform.vo.user.UserVoReq;
import com.dj.mall.product.api.ProductApi;
import com.dj.mall.product.api.dto.ProductDTO;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference
    private UserApi userApi;

    @Reference
    private ProductApi productApi;

    @RequestMapping("login")
    public ResultModel login(String username, String password) throws Exception{
        Assert.hasText(username, "用户名或密码不能为空");
        Assert.hasText(password, "用户名或密码不能为空");
        UserTokenDTO userTokenDTO = userApi.loginToken(username, password);
        return new ResultModel().success(DozerUtil.map(userTokenDTO, UserTokenVOResp.class));
    }

    /**
     * 获取盐
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
     * 普通用户注册
     * @param userVoReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel register (UserVoReq userVoReq) throws Exception {
        Assert.hasText(userVoReq.getUsername(), "请输入账号");
        Assert.hasText(userVoReq.getPassword(), "密码不能为空");
        Assert.state(userVoReq.getPassword().equals(userVoReq.getConfirmPassword()), "密码不一致");
        Assert.hasText(userVoReq.getMail(), "邮箱不能为空");
        Assert.hasText(userVoReq.getPhone(), "手机号不能为空");
        UserDto userDto = DozerUtil.map(userVoReq, UserDto.class);
        String str = "DJ" + PasswordSecurityUtil.generateRandom(6);
        userDto.setNickName(str);
        userApi.insertUser(userDto);
        return new ResultModel().success();
    }

    /**
     * 用户看到的商品列表
     * @param productVOReq
     * @return
     * @throws Exception
     */
    @RequestMapping("productList")
    public ResultModel productList(ProductVOReq productVOReq) throws Exception {
        PageResult pageInfo = productApi.allList(DozerUtil.map(productVOReq, ProductDTO.class));
        return new ResultModel().success(PageResult.pageInfo(pageInfo.getCurrent(), pageInfo.getPages(),
                DozerUtil.mapList(pageInfo.getRecords(), ProductVOResp.class)));
    }

}
