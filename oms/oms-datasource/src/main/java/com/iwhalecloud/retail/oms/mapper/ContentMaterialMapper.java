package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import com.iwhalecloud.retail.oms.entity.ContentMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ContentMaterialMapper
 * @author autoCreate
 */
@Mapper
public interface ContentMaterialMapper extends BaseMapper<ContentMaterial>{

    public  List<ContentMaterialDTO> queryContentMaterialList(@Param("contentId") Long contentId);

}