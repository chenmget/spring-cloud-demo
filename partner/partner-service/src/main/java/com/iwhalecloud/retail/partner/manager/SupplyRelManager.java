package com.iwhalecloud.retail.partner.manager;

import com.iwhalecloud.retail.partner.dto.SupplyRelDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelDeleteReq;
import com.iwhalecloud.retail.partner.entity.SupplyRel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.partner.mapper.SupplyRelMapper;


@Component
public class SupplyRelManager{
    @Resource
    private SupplyRelMapper supplyRelMapper;
    
    public int deleteSupplyRel(SupplyRelDeleteReq req){
        return supplyRelMapper.deleteBatchIds(req.getRelIds());
    }

    public SupplyRelDTO addSupplyRel(SupplyRelAddReq req){
        SupplyRel supplyRel = new SupplyRel();
        BeanUtils.copyProperties(req, supplyRel);
        int result = supplyRelMapper.insert(supplyRel);
        if(result == 0){
            return null;
        }
        SupplyRelDTO supplyRelDTO = new SupplyRelDTO();
        BeanUtils.copyProperties(supplyRel, supplyRelDTO);
        return supplyRelDTO;
    }
    
}
