package com.dj.p2p.config;


import com.alibaba.fastjson.JSON;
import com.dj.p2p.constant.CacheConstant;
import com.dj.p2p.constant.UserConstant;
import com.dj.p2p.pojo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Token拦截器
 *
 * @author lindemin
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String TOKEN = "token";

    private static final String NOT_ACTIVATION_CODE = "NOT_ACTIVATION";

    @Autowired
    private RedisTemplate redisTemplate;

    private boolean checkToken(String token) {
        if (null != token) {
            // token 有效性验证
            User user = (User) redisTemplate.opsForValue().get(CacheConstant.USER_TOKEN_ + token);
            return !(user == null);
        }
        return false;
    }

    private static boolean  loginFalse(HttpServletResponse response) throws Exception {
        Map<String,String> map = new HashMap<>();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF8");
        map.put("code","-1");
        map.put("msg","请先登录");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(map));
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        if(request.getHeader(TOKEN) == null){
            loginFalse(response);
        }
        String token = request.getHeader(TOKEN);
        User user = (User) valueOperations.get(CacheConstant.USER_TOKEN_ + token);
        if(user == null){
            loginFalse(response);
        }
        if(StringUtils.isEmpty(user.getUserStatus()) || user.getUserStatus().equals(NOT_ACTIVATION_CODE)){
            Map<String,String> map = new HashMap<>();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF8");
            map.put("code","-2");
            map.put("msg","请先认证");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(map));
            return false;
        }
        String uri = request.getRequestURI();
        if(uri.contains("manage")){
            if(StringUtils.isEmpty(user.getLevel())){
                Map<String,String> map = new HashMap<>();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF8");
                map.put("code","-4");
                map.put("msg","请先开户");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(map));
                return false;
            }
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
