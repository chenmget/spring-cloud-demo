package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreObjRelDTO;
import com.iwhalecloud.retail.warehouse.entity.ResouceStoreObjRel;
import com.iwhalecloud.retail.warehouse.mapper.ResouceStoreObjRelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ResouceStoreObjRelManager extends ServiceImpl<ResouceStoreObjRelMapper, ResouceStoreObjRel> {
    @Resource
    private ResouceStoreObjRelMapper resouceStoreObjRelMapper;

    public ResouceStoreObjRelDTO getMerchantByStore(String storeId){
        QueryWrapper<ResouceStoreObjRel> queryWrapperSuppler = new QueryWrapper<>();
        queryWrapperSuppler.eq(ResouceStoreObjRel.FieldNames.mktResStoreId.getTableFieldName(), storeId);
        ResouceStoreObjRel resouceStoreObjRel = resouceStoreObjRelMapper.selectOne(queryWrapperSuppler);
        ResouceStoreObjRelDTO dto = new ResouceStoreObjRelDTO();
        if(null == resouceStoreObjRel){
            return null;
        }else {
            BeanUtils.copyProperties(resouceStoreObjRel, dto);
            return dto;
        }
    }

    public List<String> selectStoreRelByMerchantId(String merchantId){
        QueryWrapper<ResouceStoreObjRel> queryWrapperSuppler = new QueryWrapper<>();
        queryWrapperSuppler.eq(ResouceStoreObjRel.FieldNames.objId.getTableFieldName(), merchantId);
        queryWrapperSuppler.select(ResouceStoreObjRel.FieldNames.mktResStoreId.getTableFieldName());
        List<ResouceStoreObjRel> resouceStoreObjRel = resouceStoreObjRelMapper.selectList(queryWrapperSuppler);
        ResouceStoreObjRelDTO dto = new ResouceStoreObjRelDTO();
        if(null == resouceStoreObjRel){
            return null;
        }else {
            return resouceStoreObjRel.stream().map(ResouceStoreObjRel::getMktResStoreId).collect(Collectors.toList());
        }
    }

    public Integer countStoreRelByMerchantId(String merchantId){
        QueryWrapper<ResouceStoreObjRel> queryWrapperSuppler = new QueryWrapper<>();
        queryWrapperSuppler.eq(ResouceStoreObjRel.FieldNames.objId.getTableFieldName(), merchantId);
        return resouceStoreObjRelMapper.selectCount(queryWrapperSuppler);
    }

    public int saveStoreRel(ResouceStoreObjRelDTO dto){
        ResouceStoreObjRel resouceStoreObjRel = new ResouceStoreObjRel();
        BeanUtils.copyProperties(dto, resouceStoreObjRel);
        return resouceStoreObjRelMapper.insert(resouceStoreObjRel);
    }

    public int updateStoreRel(ResouceStoreObjRelDTO dto){
        return resouceStoreObjRelMapper.updateStoreRel(dto);
    }

    public int updateStoreRelByObjId(ResouceStoreObjRelDTO dto){
        ResouceStoreObjRel resouceStoreObjRel = new ResouceStoreObjRel();
        BeanUtils.copyProperties(dto, resouceStoreObjRel);
        QueryWrapper<ResouceStoreObjRel> queryWrapperSuppler = new QueryWrapper<>();
        queryWrapperSuppler.eq(ResouceStoreObjRel.FieldNames.objId.getTableFieldName(), dto.getObjId());
        return resouceStoreObjRelMapper.update(resouceStoreObjRel, queryWrapperSuppler);
    }

    public int deleteStoreRel(String merchantId, String storeId){
        QueryWrapper<ResouceStoreObjRel> queryWrapperSuppler = new QueryWrapper<>();
        queryWrapperSuppler.eq(ResouceStoreObjRel.FieldNames.objId.getTableFieldName(), merchantId);
        queryWrapperSuppler.eq(ResouceStoreObjRel.FieldNames.mktResStoreId.getTableFieldName(), storeId);
        return resouceStoreObjRelMapper.delete(queryWrapperSuppler);
    }
}
