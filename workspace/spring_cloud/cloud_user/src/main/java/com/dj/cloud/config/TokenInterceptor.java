package com.dj.cloud.config;

import com.alibaba.fastjson.JSON;
import com.dj.cloud.pojo.User;
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
public class TokenInterceptor implements HandlerInterceptor {

private static final String TOKEN = "token";

    @Autowired
    private RedisTemplate redisTemplate;

    private boolean checkToken(String token) {
        if (null != token) {
            // token 有效性验证
            User user = (User) redisTemplate.opsForValue().get("TOKEN" + token);
            return !(user == null);
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader(TOKEN);
        if (checkToken(token)) {
            return true;
        }
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        Map<String, String> map = new HashMap<>();
        map.put("code", "success");
        map.put("msg", "success");
        writer.write(JSON.toJSONString(map));

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
