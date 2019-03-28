package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.warehouse.dto.ResouceStoreObjRelDTO;
import com.iwhalecloud.retail.warehouse.entity.ResouceStore;
import com.iwhalecloud.retail.warehouse.entity.ResouceStoreObjRel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: ResouceStoreObjRelMapper
 * @author autoCreate
 */
@Mapper
public interface ResouceStoreObjRelMapper extends BaseMapper<ResouceStoreObjRel>{

    int updateStoreRel(ResouceStoreObjRelDTO dto);

}