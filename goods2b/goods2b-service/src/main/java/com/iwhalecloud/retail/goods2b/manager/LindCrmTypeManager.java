package com.iwhalecloud.retail.goods2b.manager;

import com.iwhalecloud.retail.goods2b.entity.ProdCRMType;
import com.iwhalecloud.retail.goods2b.mapper.ProdCRMTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wzy
 * @date 2019/7/11
 */
@Component
public class LindCrmTypeManager {
    @Resource
    private ProdCRMTypeMapper prodCRMTypeMapper;

    public ProdCRMType getProCRMType(String lindCrmTypeId){
        return prodCRMTypeMapper.selectById(lindCrmTypeId);
    }
}
