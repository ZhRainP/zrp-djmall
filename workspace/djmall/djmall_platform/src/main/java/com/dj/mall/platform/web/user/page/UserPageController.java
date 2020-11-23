package com.dj.mall.platform.web.user.page;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.autr.api.address.AddressApi;
import com.dj.mall.autr.api.dto.address.AddressDTO;
import com.dj.mall.autr.api.dto.shopcar.CarDTO;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.shopcar.CarApi;
import com.dj.mall.autr.api.user.UserApi;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.common.util.PasswordSecurityUtil;
import com.dj.mall.dict.api.dictionary.DictionaryApi;
import com.dj.mall.dict.api.dto.dictionary.DictionaryDTO;
import com.dj.mall.platform.vo.UserTokenReq;
import com.dj.mall.platform.vo.shopcar.CarVOReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserPageController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Reference
    private UserApi userApi;
    @Reference
    private CarApi carApi;
    @Reference
    private AddressApi addressApi;
    @Reference
    private DictionaryApi dictionaryApi;

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

    /**
     * 根据购物车ID查询已勾选商品
     * @param TOKEN　token
     * @param userTokenReq　用户信息
     * @param model
     * @return
     */
    @RequestMapping("toSet")
    public String toSet (String TOKEN, UserTokenReq userTokenReq, ModelMap model) {
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get("TOKEN_" + TOKEN);
        userTokenReq.setUserId(userDto.getId());
        UserDto userDto1 = userApi.findUserById(userDto.getId());
        model.put("user", userDto1);
        List<CarDTO> carDTO = carApi.findCarById(userDto.getId());
        model.put("car", carDTO);
        List<AddressDTO> list = addressApi.findAddressList(userDto.getId());
        model.put("address", list);
        List<DictionaryDTO> dictionaryDTOList = dictionaryApi.findDictByPCode("PAY_TYPE");
        model.put("dictList", DozerUtil.mapList(dictionaryDTOList, DictionaryDTO.class));
        return "mall/set_list";
    }
}
 