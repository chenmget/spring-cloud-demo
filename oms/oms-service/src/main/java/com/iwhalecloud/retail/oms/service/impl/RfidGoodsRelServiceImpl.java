package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.TRfidGoodsRelDTO;
import com.iwhalecloud.retail.oms.manager.RfidGoodsRelManager;
import com.iwhalecloud.retail.oms.service.RfidGoodsRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class RfidGoodsRelServiceImpl implements RfidGoodsRelService {

    @Autowired
    private RfidGoodsRelManager rfidGoodsRelManager;

    @Override
    public int addRfidGoodsRel(TRfidGoodsRelDTO t) {
        if (StringUtils.isEmpty(t.getRfid()) || StringUtils.isEmpty(t.getGoodsId())) {
            return 0;
        }
        return rfidGoodsRelManager.insert(t);
    }

    @Override
    public int modifyRfidGoodsRel(TRfidGoodsRelDTO t) {
        int total= rfidGoodsRelManager.modify(t);
        return total;
    }

    @Override
    public List<TRfidGoodsRelDTO> getRfidGoodsRel(TRfidGoodsRelDTO t) {
        return rfidGoodsRelManager.select(t);
    }

    @Override
    public boolean removeRfidGoodsRel(TRfidGoodsRelDTO rfidGoodsRelDTO) {
        return rfidGoodsRelManager.remove(rfidGoodsRelDTO);
    }

}
