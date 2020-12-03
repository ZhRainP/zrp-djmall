package com.dj.p2p.config;


import com.alibaba.fastjson.JSON;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.constant.UserConstant;
import com.dj.p2p.pojo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Token拦截器
 */
@Component
public class PowerInterceptor implements HandlerInterceptor {

    private static final String TOKEN = "token";

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        Map<String, String> map = new HashMap<>();
        // 获取请求中的token
        String token = httpServletRequest.getHeader(TOKEN);
        // token 有效性验证
        User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
        //判断用户级别状态
        if (user.getLevel().equals("") || user.getLevel() == null) {
            PrintWriter writer = httpServletResponse.getWriter();
            map.put("code", "-6");
            map.put("msg", "没有权限");
            writer.write(JSON.toJSONString(map));
            writer.flush();
            return false;
        }
        if (!user.getLevel().equals(UserConstant.RISK_CONTROL_SPECIALIST_CODE) && !user.getLevel().equals(UserConstant.RISK_CONTROL_MANAGER_CODE)
                && !user.getLevel().equals(UserConstant.DIRECTOR_RISK_CONTROL_CODE)) {
            PrintWriter writer = httpServletResponse.getWriter();
            map.put("code", "-6");
            map.put("msg", "没有权限");
            writer.write(JSON.toJSONString(map));
            writer.flush();
            return false;
        }
        return true;


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}


