package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.CataLogDTO;
import com.iwhalecloud.retail.oms.entity.Catalog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ContentPicMapper
 * @author autoCreate
 */
@Mapper
public interface CatalogMapper extends BaseMapper<Catalog>{

    List<CataLogDTO> queryCatalog(@Param("cataId") Long cataId);

}