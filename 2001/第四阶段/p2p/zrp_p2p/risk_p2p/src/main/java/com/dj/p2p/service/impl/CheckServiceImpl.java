package com.dj.p2p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.pojo.subject.Check;
import com.dj.p2p.mapper.CheckMapper;
import com.dj.p2p.service.CheckService;
import org.springframework.stereotype.Service;


@Service
public class CheckServiceImpl extends ServiceImpl<CheckMapper, Check> implements CheckService {

}
