package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.iwhalecloud.retail.goods2b.dto.TypeDTO;
import com.iwhalecloud.retail.goods2b.entity.Type;
import com.iwhalecloud.retail.goods2b.mapper.TypeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chengxu
 */
@Component
public class TypeManager{
    @Resource
    private TypeMapper typeMapper;

    public String saveType(TypeDTO typeDTO){
        Type type = new Type();
        BeanUtils.copyProperties(typeDTO, type);
        String id = IdWorker.getIdStr();
        type.setTypeId(id);
        int ret = typeMapper.insert(type);
        if(ret < 1){
            return "0";
        }else{
            return type.getTypeId();
        }
    }

    public int updateType(TypeDTO typeDTO){
        Type type = new Type();
        BeanUtils.copyProperties(typeDTO, type);
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Type.FieldNames.typeId.getTableFieldName(), typeDTO.getTypeId());
        return typeMapper.update(type, queryWrapper);
    }

    public int deleteType(String typeId){
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Type.FieldNames.typeId.getTableFieldName(), typeId);
        return typeMapper.delete(queryWrapper);
    }

    public List<Type> listTypeByName(String name){
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like(Type.FieldNames.typeName.getTableFieldName(), name);
        }
        queryWrapper.orderByAsc(Type.FieldNames.typeOrder.getTableFieldName());
        return typeMapper.selectList(queryWrapper);
    }

    public Type selectById(String typeId){
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Type.FieldNames.typeId.getTableFieldName(),typeId);
        return typeMapper.selectOne(queryWrapper);
    }

}
