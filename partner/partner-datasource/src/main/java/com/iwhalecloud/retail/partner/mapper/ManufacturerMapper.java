package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.ManufacturerDTO;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerPageReq;
import com.iwhalecloud.retail.partner.entity.Manufacturer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ManufacturerMapper
 * @author autoCreate
 */
@Mapper
public interface ManufacturerMapper extends BaseMapper<Manufacturer>{

    Page<ManufacturerDTO> pageManufacturer(Page<ManufacturerDTO> page, @Param("req") ManufacturerPageReq req);

}