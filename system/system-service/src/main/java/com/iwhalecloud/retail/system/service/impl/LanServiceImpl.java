package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.LanDTO;
import com.iwhalecloud.retail.system.entity.Lan;
import com.iwhalecloud.retail.system.manager.LanManager;
import com.iwhalecloud.retail.system.service.LanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
public class LanServiceImpl implements LanService {

    @Autowired
    private LanManager lanManager;

    @Override
    public ResultVO listLans() {
        List<Lan> lans = lanManager.listLan();
        List<LanDTO> lanDTOS = new ArrayList<>();
        for(Lan l : lans){
            LanDTO dto = new LanDTO();
            BeanUtils.copyProperties(l, dto);
            lanDTOS.add(dto);
        }
        return ResultVO.success(lanDTOS);
    }
}