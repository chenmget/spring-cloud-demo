package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.dto.ProdCRMTypeDto;
import com.iwhalecloud.retail.goods2b.dto.req.ProdCrmTypeReq;
import com.iwhalecloud.retail.goods2b.entity.MerchantTagRel;
import com.iwhalecloud.retail.goods2b.entity.ProdCRMType;
import com.iwhalecloud.retail.goods2b.mapper.ProdCRMTypeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    public List<ProdCRMTypeDto>  queryProdCrmType(ProdCrmTypeReq req){
        QueryWrapper<ProdCRMType> queryWrapper = new QueryWrapper<ProdCRMType>();
        List<ProdCRMTypeDto> prodCRMTypeDtos = new ArrayList<>();
        if(StringUtils.isNotBlank(req.getTypeId())){
            queryWrapper.eq(ProdCRMType.FieldNames.typeId.getTableFieldName(),req.getTypeId());
        }
        if(StringUtils.isNotBlank(req.getParentTypeId())){
            queryWrapper.eq(ProdCRMType.FieldNames.parentTypeId.getTableFieldName(),req.getParentTypeId());
        }
        if(StringUtils.isNotBlank(req.getIsDeleted())) {
            queryWrapper.eq(ProdCRMType.FieldNames.isDeleted.getTableFieldName(), req.getIsDeleted());
        }

        List<ProdCRMType> rtList = prodCRMTypeMapper.selectList(queryWrapper);
        for (ProdCRMType prodCRMType : rtList){
            ProdCRMTypeDto prodCRMTypeDto = new ProdCRMTypeDto();
            BeanUtils.copyProperties(prodCRMType,prodCRMTypeDto);
            prodCRMTypeDtos.add(prodCRMTypeDto);
        }
        return prodCRMTypeDtos;
    }
}
