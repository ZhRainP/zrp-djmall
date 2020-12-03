package com.dj.p2p.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.p2p.riskcontrol.RiskControl;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface RiskControlMapper extends BaseMapper<RiskControl> {
    List<RiskControl> getList() throws DataAccessException;

    RiskControl getP2pFinancial(Integer id) throws DataAccessException;
}
