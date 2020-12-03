package com.dj.cloud.client;

import com.dj.cloud.config.ResultModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service", path = "/order/", fallback = OrderApiFullBack.class)
public interface OrderApi {

//    @RequestMapping("test")
//    String buy (@RequestParam("buy") String buy);
//
//    @RequestMapping("getUser")
//    User getUser(@RequestBody User user);

    @PostMapping("findList")
    ResultModel list();
}
