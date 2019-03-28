package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerRelShopDTO;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerShopListReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerShopQueryListReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerShopQueryPageReq;
import com.iwhalecloud.retail.partner.entity.PartnerShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: PartnerShopMapper
 * @author autoCreate
 */
@Mapper
public interface PartnerShopMapper extends BaseMapper<PartnerShop> {
    /**
     * 分页查询代理商列表
     * @param req
     * @return
     */
    Page<PartnerRelShopDTO> pagePartnerNearby(Page<PartnerRelShopDTO> page, @Param("req")PartnerShopListReq req);

    /**
     * 分页查询厅店列表
     * @param page
     * @param req
     * @return
     */
	Page<PartnerShopDTO> queryPartnerShopPage(Page<PartnerShopDTO> page,@Param("req") PartnerShopQueryPageReq req);

    /**
     * 查询厅店列表(不分页）
     * @param req
     * @return
     */
    List<PartnerShopDTO> queryPartnerShopList( @Param("req") PartnerShopQueryListReq req);

}