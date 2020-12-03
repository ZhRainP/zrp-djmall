package com.dj.cloud.controller;

import com.dj.cloud.config.ResultModel;
import com.dj.cloud.pojo.Order;
import com.dj.cloud.pojo.User;
import com.dj.cloud.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "订单api")
@RestController
@RequestMapping("order")
public class OderController {
    @Autowired
    private OrderService orderService;
//    @RequestMapping("test")
//    public String test (String buy) {
//        return "buy" +"\t"+ buy;
//    }
//
//    @RequestMapping("getUser")
//    public User getUser(@RequestBody User user) {
//        return user;
//    }

    @ApiOperation("订单列表")
    @PostMapping("findList")
    public ResultModel findList() throws Exception{
        List<Order> list = orderService.list();
        return new ResultModel().success(list);
    }

    @ApiOperation("添加订单")
    @PostMapping("add")
    public ResultModel add (@RequestBody Order order) throws Exception {
        orderService.save(order);
        return new ResultModel().success();
    }

    @ApiOperation("根据查询订单")
    @ApiImplicitParam(name = "id", value = "订单id")
    @PostMapping("findOrderById/{id}")
    public ResultModel findOrderById (@PathVariable Integer id) throws Exception {
        Order order = orderService.getById(id);
        return new ResultModel().success(order);
    }

    @ApiOperation("修改订单")
    @PostMapping("update")
    public ResultModel update (@RequestBody Order order) throws Exception {
        orderService.updateById(order);
        return new ResultModel().success();
    }

    @ApiOperation("删除订单")
    @ApiImplicitParam(name = "id", value = "订单id")
    @PostMapping("delete/{id}")
    public ResultModel delete (@PathVariable Integer id) throws Exception {
        orderService.removeById(id);
        return new ResultModel().success();
    }
}
