package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.SupplyProductRelDTO;
import com.iwhalecloud.retail.partner.dto.resp.SupplyProductQryResp;
import com.iwhalecloud.retail.partner.entity.SupplyProductRel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import com.iwhalecloud.retail.partner.mapper.SupplyProductRelMapper;

import java.util.HashMap;
import java.util.Map;


@Component
public class SupplyProductRelManager {
    @Resource
    private SupplyProductRelMapper supplyProductRelMapper;


    public Page<SupplyProductQryResp> querySupplyProduct(SupplyProductRelDTO dto) {
        Page<SupplyProductQryResp> page = new Page<SupplyProductQryResp>(dto.getPageNo(), dto.getPageSize());
        return supplyProductRelMapper.querySupplyProduct(page, dto);
    }

    public int bindSupplyProduct(SupplyProductRelDTO dto) {
        dto.setRelId(null);
        SupplyProductRel supplyProductRel = new SupplyProductRel();
        BeanUtils.copyProperties(dto, supplyProductRel);
        return supplyProductRelMapper.insert(supplyProductRel);
    }

    public int unBindSupplyProduct(SupplyProductRelDTO dto) {
        String relId = dto.getRelId();
        if (relId != null) {
            Map map = new HashMap();
            map.put("rel_id", relId);
            return supplyProductRelMapper.deleteByMap(map);
        }
        return 0;
    }
}
