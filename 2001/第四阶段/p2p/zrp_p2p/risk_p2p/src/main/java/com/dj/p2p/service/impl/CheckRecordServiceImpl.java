package com.dj.p2p.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.p2p.pojo.subject.CheckRecord;
import com.dj.p2p.service.CheckRecordService;
import com.dj.p2p.mapper.CheckRecordMapper;
import org.springframework.stereotype.Service;


@Service
public class CheckRecordServiceImpl extends ServiceImpl<CheckRecordMapper, CheckRecord> implements CheckRecordService {

}
