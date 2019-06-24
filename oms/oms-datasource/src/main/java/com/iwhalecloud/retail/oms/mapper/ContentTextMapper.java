package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.ContentTextDTO;
import com.iwhalecloud.retail.oms.entity.ContentText;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ContentTextMapper
 * @author autoCreate
 */
@Mapper
public interface ContentTextMapper extends BaseMapper<ContentText>{

    public List<ContentTextDTO> queryContentText(@Param("contentid")Long contentid);

}