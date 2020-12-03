package com.dj.p2p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.mapper.LoginCountMapper;
import com.dj.p2p.pojo.logincount.LoginCount;
import com.dj.p2p.service.LoginCountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ldm
 * @since 2020-11-28
 */
@Service
public class LoginCountServiceImpl extends ServiceImpl<LoginCountMapper, LoginCount> implements LoginCountService {

}
