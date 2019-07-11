package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ProdTypeMapper
 * @author autoCreate
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type>{

    public List<Type> selectAllSubTypeById(@Param("typeId") String typeId);
}