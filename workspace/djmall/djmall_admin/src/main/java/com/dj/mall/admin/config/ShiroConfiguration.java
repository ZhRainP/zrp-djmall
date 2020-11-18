package com.dj.mall.admin.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 */
@Configuration
@DependsOn("shiroRealm")
public class ShiroConfiguration {

    /**
     * 自定义Realm
     */
    @Autowired
    private ShiroRealm shiroRealm;

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean
    DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * Shiro过滤器工厂
     *
     * @param securityManager
     * @return
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 定义 shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置默认登录的 URL，身份认证失败会访问该 URL
        shiroFilterFactoryBean.setLoginUrl("/user/toLogin");
        // 设置成功之后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/common/toIndex");
        // 设置未授权界面，权限认证失败会访问该 URL
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.jsp");
        Map<String, String> filters = new LinkedHashMap<>();
        filters.put("/user/login", "anon"); // anon 表示不需要认证
        filters.put("/user/toLogin", "anon"); // anon 表示不需要认证
        filters.put("/user/toRegister", "anon"); // anon 表示不需要认证
        filters.put("/user/register", "anon"); // anon 表示不需要认证
        filters.put("/static/**", "anon"); // anon 表示不需要认证
        filters.put("/user/findSalt", "anon"); // anon 表示不需要认证
        filters.put("/user/active/**", "anon"); // anon 表示不需要认证
        filters.put("/user/toResetPwd/**", "anon"); // anon 表示不需要认证
        filters.put("/user/toForgetPwd", "anon"); // anon 表示不需要认证
        filters.put("/user/getVerifyCode/**", "anon"); // anon 表示不需要认证
        filters.put("/user/sendSms", "anon"); // anon 表示不需要认证
        filters.put("/user/forgetPwd", "anon"); // anon 表示不需要认证
        filters.put("/**", "authc"); // authc 表示必须认证才可访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filters);
        return shiroFilterFactoryBean;
    }

}
