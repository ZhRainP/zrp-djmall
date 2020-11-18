package com.dj.mall.platform;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("mall")
public class IndexPageController {
    /**
     * 去商城页面
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex () {
        return "common/index";
    }



}
