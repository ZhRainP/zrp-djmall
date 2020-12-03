package com.dj.p2p.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.mapper.IP2pExaTraMapper;
import com.dj.p2p.riskcontrol.P2pExaTra;
import com.dj.p2p.service.IP2pExaTraService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class IP2pExaTraServiceImpl extends ServiceImpl<IP2pExaTraMapper, P2pExaTra> implements IP2pExaTraService {


}
