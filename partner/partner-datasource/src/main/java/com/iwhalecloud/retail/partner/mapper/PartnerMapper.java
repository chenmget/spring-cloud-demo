package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierReq;
import com.iwhalecloud.retail.partner.entity.Partner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: PartnerMapper
 * @author autoCreate
 */
@Mapper
public interface PartnerMapper extends BaseMapper<Partner>{

    Page<PartnerDTO> querySupplyRel(Page<PartnerDTO> page, @Param("pageReq") SupplierReq supplierReq);

    /**
     * 根据在页面上选择的代理商名称，状态查询代理商列表
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    Page<PartnerDTO> pagePartner(Page<PartnerDTO> page, @Param("pageReq") PartnerPageReq pageReq);

}