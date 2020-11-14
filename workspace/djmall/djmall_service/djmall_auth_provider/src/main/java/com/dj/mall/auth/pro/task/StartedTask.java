package com.dj.mall.auth.pro.task;

import com.dj.mall.autr.api.dto.resource.ResourceDTO;
import com.dj.mall.autr.api.dto.role.RoleDTO;
import com.dj.mall.autr.api.role.RoleApi;
import com.dj.mall.common.constant.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StartedTask {
    @Autowired
    private RoleApi roleApi;

    @Autowired
    private RedisTemplate redisTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initRoleCache(){
        log.info("----init role cache start----");
        //角色对应资源存入redis
        List<RoleDTO> allRole = roleApi.findList();
        HashOperations hashOperations = redisTemplate.opsForHash();
        allRole.forEach(roleDTO -> {
            //角色对应资源
            List<ResourceDTO> roleResourceList = roleApi.getRoleResourceByRoleId(roleDTO.getId());
            hashOperations.put(CacheConstant.ROLE_ALL_KEY, CacheConstant.RILE_ID_PREFIX + roleDTO.getId(), roleResourceList);
        });

        log.info("----init role cache end----");
    }
}
