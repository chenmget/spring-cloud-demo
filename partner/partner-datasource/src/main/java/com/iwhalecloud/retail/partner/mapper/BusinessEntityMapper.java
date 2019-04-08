package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityPageByRightsReq;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityPageReq;
import com.iwhalecloud.retail.partner.entity.BusinessEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: BusinessEntityMapper
 * @author autoCreate
 */
@Mapper
public interface BusinessEntityMapper extends BaseMapper<BusinessEntity>{

    Page<BusinessEntityDTO> pageBusinessEntity(Page<BusinessEntityDTO> page, @Param("req") BusinessEntityPageReq req);

    /**
     * 查询有权限的经营主体
     * @param req
     * @return
     */
    Page<BusinessEntityDTO> pageBusinessEntityByRight(Page<BusinessEntityDTO> page, @Param("req") BusinessEntityPageByRightsReq req);


}