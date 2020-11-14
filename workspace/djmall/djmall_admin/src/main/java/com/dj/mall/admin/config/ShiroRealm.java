package com.dj.mall.admin.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.autr.api.dto.user.UserDto;
import com.dj.mall.autr.api.user.UserApi;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.constant.CacheConstant;
import com.dj.mall.common.constant.CodeConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义Realm
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Reference
    private UserApi userApi;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 创建简单授权信息
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        //当前登陆用户的权限资源
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getSession().getAttribute(CodeConstant.USER_SESSION);
        HashOperations hashOperations = redisTemplate.opsForHash();
        List<ResourceDTO> permissionList = (List<ResourceDTO>) hashOperations.get(CacheConstant.ROLE_ALL_KEY,
                CacheConstant.RILE_ID_PREFIX + userDto.getRoleId());
//        List<ResourceDTO> permissionList = userDto.getPermissionList();
        List<String> collect = permissionList.stream().map(ResourceDTO::getResourceCode).collect(Collectors.toList());
        simpleAuthorInfo.addStringPermissions(collect);
        return simpleAuthorInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 得到用户名
        String username = (String) authenticationToken.getPrincipal();
        // 得到密码
        String password = new String((char[]) authenticationToken.getCredentials());

        try{
            UserDto userDto = userApi.findUserNameAndPwd(username, password);
            SecurityUtils.getSubject().getSession().setAttribute("user", userDto);
        } catch (BusinessException be) {
            throw new AccountException(be.getErrorMsg());
        }
        //表示认证通过
        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
