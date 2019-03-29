package com.iwhalecloud.retail.warehouse.manager;

import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceBatchRecDTO;
import com.iwhalecloud.retail.warehouse.entity.ResourceBatchRec;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.warehouse.mapper.ResourceBatchRecMapper;

import java.util.Calendar;


@Component
public class ResourceBatchRecManager{

    @Resource
    private ResourceBatchRecMapper resourceBatchRecMapper;

    @Resource
    private ResourceInstMapper resourceInstMapper;

    public String insertResourceBatchRec(ResourceBatchRecDTO batchRecDTO){
        ResourceBatchRec batchRec = new ResourceBatchRec();
        BeanUtils.copyProperties(batchRecDTO,batchRec);
        batchRec.setCreateDate(Calendar.getInstance().getTime());
        batchRec.setMktResBatchNbr(resourceInstMapper.getPrimaryKey());
        batchRec.setStatusDate(Calendar.getInstance().getTime());
        batchRec.setStatusCd(ResourceConst.VALID);
        resourceBatchRecMapper.insert(batchRec);
        return batchRec.getMktResBatchId();
    }
    
    
    
}
