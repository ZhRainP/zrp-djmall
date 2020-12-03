package com.dj.cloud.client;

import com.dj.cloud.config.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderApiFullBack implements OrderApi{
    @Override
    public ResultModel list() {
        log.info("服务降级");
        return null;
    }

}
