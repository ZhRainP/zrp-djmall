package com.dj.p2p.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired PowerInterceptor powerInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(tokenInterceptor);
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/user/login");
        interceptorRegistration.excludePathPatterns("/swagger-ui.html/**");
        interceptorRegistration.excludePathPatterns("/swagger-resources/**");
        interceptorRegistration.excludePathPatterns("/webjars/**");
        interceptorRegistration.excludePathPatterns("/v2/**");
        interceptorRegistration.excludePathPatterns("/user/returnAddUserMessage");
        interceptorRegistration.excludePathPatterns("/user/returnAddMessage");
        interceptorRegistration.excludePathPatterns("/user/returnAddCompanyUserMessage");
        interceptorRegistration.excludePathPatterns("/user/addUser");
        interceptorRegistration.excludePathPatterns("/user/addCompanyUser");
        interceptorRegistration.excludePathPatterns("/dict/selectDictByParentCode");
        interceptorRegistration.excludePathPatterns("/dict/returnDictMessage");
        interceptorRegistration.excludePathPatterns("/user/userCertification");
        interceptorRegistration.excludePathPatterns("/manage/say");
        InterceptorRegistration powerInterceptorRegistration = registry.addInterceptor(powerInterceptor);
        powerInterceptorRegistration.addPathPatterns("/subject/**");
        powerInterceptorRegistration.addPathPatterns("/user/userList");
        powerInterceptorRegistration.addPathPatterns("/user/updateUserLockStatus");
        powerInterceptorRegistration.addPathPatterns("/user/returnHomePageMessage");
        powerInterceptorRegistration.excludePathPatterns("/subject/returnOutBorrowMessage");
        powerInterceptorRegistration.excludePathPatterns("/subject/returnBuyMessage");
        powerInterceptorRegistration.excludePathPatterns("/subject/buySubject");
        powerInterceptorRegistration.excludePathPatterns("/subject/returnBorrowMessage");
        powerInterceptorRegistration.excludePathPatterns("/subject/sign");
        powerInterceptorRegistration.excludePathPatterns("/subject/overSubject");
        powerInterceptorRegistration.excludePathPatterns("/subject/returnMyOutBorrow");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
