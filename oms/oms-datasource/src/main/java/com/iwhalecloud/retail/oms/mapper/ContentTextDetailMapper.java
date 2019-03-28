package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.oms.dto.ContentTextDetailDTO;
import com.iwhalecloud.retail.oms.entity.ContentTextDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ContentTextDetailMapper
 * @author autoCreate
 */
@Mapper
public interface ContentTextDetailMapper extends BaseMapper<ContentTextDetail>{

    public List<ContentTextDetailDTO> queryContentTextDetail(@Param("textid")  Long textid);

}