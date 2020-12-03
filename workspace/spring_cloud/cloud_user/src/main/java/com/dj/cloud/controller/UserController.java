package com.dj.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.cloud.client.OrderApi;
import com.dj.cloud.config.ResultModel;
import com.dj.cloud.pojo.Token;
import com.dj.cloud.pojo.User;
import com.dj.cloud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Api(tags = "用户api")
@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class UserController {
    @Autowired
    private OrderApi orderApi;
    @Autowired
    private UserService userService;

    @ApiOperation("用户列表")
    @PostMapping("userList")
    public ResultModel userList(@RequestHeader String token) throws Exception {
        log.info("进入用户列表展示");
        List<User> list = userService.list();
        log.info("进入用户列表展示结束");
        return new ResultModel().success(list);
    }

    @ApiOperation("添加用户")
    @PostMapping("add")
    public ResultModel add(@RequestBody User user) throws Exception {
        log.info("进入添加用户:{}", user);
        userService.save(user);
        log.info("进入用户添加结束:{}", user);
        return new ResultModel().success();
    }

    @ApiOperation("根据主键查找用户信息")
    @ApiImplicitParam(name = "id", value = "用户id")
    @PostMapping("findUserById/{id}")
    public ResultModel findUserById(@PathVariable Integer id) throws Exception {
        log.info("进入根据主键查找用户P:{}", id);
        User user = userService.getById(id);
        log.info("进入根据主键查找用户结束:{}", id);
        return new ResultModel().success(user);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("update")
    public ResultModel update(@RequestBody User user) throws Exception {
        log.info("进入修改用户:{}", user);
        userService.updateById(user);
        log.info("进入修改用户结束:{}", user);
        return new ResultModel().success();
    }

    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "id", value = "用户id")
    @PostMapping("deleteUser/{id}")
    public ResultModel delete(@PathVariable Integer id) throws Exception {
        log.info("进入根据主键删除用户：{}", id);
        userService.removeById(id);
        log.info("进入根据主键主键用户{}", id);
        return new ResultModel().success();
    }

    @ApiOperation("订单列表")
    @PostMapping("findList")
    public ResultModel findList() throws Exception {
        return orderApi.list();
    }

    @PostMapping("login")
    public ResultModel<Object> login(String password, String username) throws Exception {
        log.info("进入登陆用户：{},{}", password,username);
        return userService.findUsernameAndPassword(password, username);
    }
}

