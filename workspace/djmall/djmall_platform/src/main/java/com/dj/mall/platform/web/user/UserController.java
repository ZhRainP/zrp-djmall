package com.dj.mall.platform.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.autr.api.address.AddressApi;
import com.dj.mall.autr.api.area.AreaApi;
import com.dj.mall.autr.api.dto.address.AddressDTO;
import com.dj.mall.autr.api.dto.area.AreaDTO;
import com.dj.mall.autr.api.dto.shopcar.CarDTO;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.dto.user.UserTokenDTO;
import com.dj.mall.autr.api.shopcar.CarApi;
import com.dj.mall.autr.api.user.UserApi;
import com.dj.mall.common.base.PageResult;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.platform.vo.UserTokenReq;
import com.dj.mall.platform.vo.UserTokenVOResp;
import com.dj.mall.platform.vo.address.AddressVOReq;
import com.dj.mall.platform.vo.address.AddressVOResp;
import com.dj.mall.platform.vo.area.AreaVOResp;
import com.dj.mall.platform.vo.product.ProductVOReq;
import com.dj.mall.platform.vo.product.ProductVOResp;
import com.dj.mall.platform.vo.shopcar.CarVOReq;
import com.dj.mall.platform.vo.user.UserVoReq;
import com.dj.mall.product.api.ProductApi;
import com.dj.mall.product.api.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference
    private UserApi userApi;

    @Reference
    private ProductApi productApi;

    @Reference
    private CarApi carApi;

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private AreaApi areaApi;

    @Reference
    private AddressApi addressApi;

    /**
     * 普通用户登陆
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
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

    /**
     * 添加到购物车
     * @param carVOReq 购物车信息
     * @return
     * @throws Exception
     */
    @RequestMapping("addShop")
    public ResultModel addShop (CarVOReq carVOReq, String TOKEN) throws Exception {
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        carVOReq.setUserId(userDto.getId());
        carApi.addShop(DozerUtil.map(carVOReq, CarDTO.class));
        return new ResultModel().success();
    }

    /**
     * 改变勾选状态
     * @param carId 购物车ID
     * @param checkStatus
     * @return
     * @throws Exception
     */
    @RequestMapping("updateStatus")
    public ResultModel updateStatus (Integer carId, Integer checkStatus) throws Exception{
        carApi.updateStatus(carId, checkStatus);
        return new ResultModel().success();
    }

    /**
     * 后悔了不想要
     * @param carId 根据购物车ID删除
     * @return
     * @throws Exception
     */
    @RequestMapping("delCarByCarId")
    public ResultModel delCarByCarId(Integer carId) throws Exception{
        carApi.delCarByCarId(carId);
        return new ResultModel().success();
    }

    /**
     * 收货地址列表
     * @param TOKEN
     * @param userTokenReq
     * @return
     * @throws Exception
     */
    @RequestMapping("addressList")
    public ResultModel addressList(String TOKEN, UserTokenReq userTokenReq) throws Exception {
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        userTokenReq.setUserId(userDto.getId());
        List<AddressDTO> list = addressApi.findAddressList(userDto.getId());
        return new ResultModel().success(DozerUtil.mapList(list, AddressVOResp.class));
    }

    /**
     * 省市县级联
     * @param pId
     * @return
     * @throws Exception
     */
    @RequestMapping("areaLian")
    public ResultModel areaLian(Integer pId, ModelMap model) throws Exception{
        List<AreaDTO> list = areaApi.cascadeList(pId);
        model.put("area", list);
        return new ResultModel().success(DozerUtil.mapList(list, AreaVOResp.class));
    }

    /**
     * 添加收货地址
     * @param addressVOReq 收货信息
     * @return
     * @throws Exception
     */
    @RequestMapping("addAddress")
     public ResultModel addAddress(AddressVOReq addressVOReq, String TOKEN) throws Exception{
        Assert.hasText(addressVOReq.getAddress(), "请填写详细地址");
        Assert.hasText(addressVOReq.getPhone(), "手机号必须给老子填好，不然要我猜吗？");
        Assert.hasText(addressVOReq.getUserName(), "不填名字，是给鬼买的吗");
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        addressVOReq.setUserId(userDto.getId());
        addressApi.addAddress(DozerUtil.map(addressVOReq, AddressDTO.class));
        return new ResultModel().success();
     }

    /**
     * 个人信息管理
     * @param userVoReq
     * @return
     * @throws Exception
     */
     @RequestMapping("updatePim")
    public ResultModel updatePim (UserVoReq userVoReq) throws Exception{
        Assert.state(!userVoReq.getNickName().equals(userVoReq.getUsername()),"昵称不能和用户名一样(⊙o⊙)哦");
        userApi.updateUserPim(DozerUtil.map(userVoReq, UserDto.class));
        return new ResultModel().success();
     }
}
