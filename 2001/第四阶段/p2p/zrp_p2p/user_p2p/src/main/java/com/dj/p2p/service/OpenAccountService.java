package com.dj.p2p.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.p2p.common.BusinessException;
import com.dj.p2p.common.ResultModel;
import com.dj.p2p.pojo.openaccount.OpenAccount;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ldm
 * @since 2020-11-27
 */
public interface OpenAccountService extends IService<OpenAccount> {

    /**
     * 返回用户开户信息回显
     *
     * @param token 用户令牌
     * @return
     * @throws BusinessException
     */
    ResultModel returnUserOpenAccountMessage(String token) throws BusinessException;
}
