package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDTO;
import com.iwhalecloud.retail.oms.entity.ShelfTemplates;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShelfTemplatesMapper extends BaseMapper<ShelfTemplates> {

    int editShelfTemplates(ShelfTemplatesDTO dto);

    Page<ShelfTemplatesDTO> queryShelfTemplates(Page<ShelfTemplatesDTO> page, ShelfTemplatesDTO request);

    int deleteShelfTemplates(ShelfTemplates dto);
}
