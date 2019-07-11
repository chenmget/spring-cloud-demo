package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.entity.MerchantTagRel;
import com.iwhalecloud.retail.goods2b.entity.ProdCRMType;
import com.iwhalecloud.retail.goods2b.mapper.ProdCRMTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wzy
 * @date 2019/7/11
 */
@Component
public class LindCrmTypeManager {
    @Resource
    private ProdCRMTypeMapper prodCRMTypeMapper;

    public List<ProdCRMType> getProCRMType(String lindCrmTypeId){
        QueryWrapper<ProdCRMType> queryWrapper = new QueryWrapper<ProdCRMType>();
        if (lindCrmTypeId != null) {
            queryWrapper.eq(ProdCRMType.FieldNames.parentTypeId.getTableFieldName(), lindCrmTypeId);
        }
        return  prodCRMTypeMapper.selectList(queryWrapper);
    }
}
