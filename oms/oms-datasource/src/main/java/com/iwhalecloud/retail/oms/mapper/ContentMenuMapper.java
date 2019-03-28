package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.CataLogDTO;
import com.iwhalecloud.retail.oms.entity.TCatalog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentMenuMapper extends BaseMapper<TCatalog> {
    int createContentMenu(TCatalog dto);

    int deleteContentMenu(CataLogDTO dto);

    int editContentMenu(CataLogDTO dto);

    List<CataLogDTO> queryContentMenuList(CataLogDTO dto);

    CataLogDTO queryContentMenuDetail(CataLogDTO dto);

    int updateContentBase(CataLogDTO dto);

    int moveContentMenu(CataLogDTO dto);

    List<CataLogDTO> queryMenuByParentAndName(CataLogDTO dto);
}
