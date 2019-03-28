package com.iwhalecloud.retail.partner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierReq;

import java.util.List;

public interface PartnerService{

    /**
     * 可供代理商查询
     * @param supplierReq
     * @return
     */
    ResultVO<Page<PartnerDTO>> querySupplyRel(SupplierReq supplierReq);

    /**
     * 根据店铺ID 查找对应的 分销商列表
     * @author zwl
     * @param partnerShopId
     * @return
     */
    List<PartnerDTO> getPartnerListByShopId(String partnerShopId);


    /**
     * 根据在页面上选择的代理商名称，状态查询代理商列表
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    Page<PartnerDTO> pagePartner(PartnerPageReq page);

    /**
     * 查找代理商详情
     * @param partnerId 代理商ID
     * @return
     * @date 2018/11/15 15:27
     */
    PartnerDTO getPartnerById(String partnerId);

    /**
     * 查找代理商详情
     * @param partnerCode 代理商code
     * @return
     * @date 2018/11/15 15:27
     */
    PartnerDTO getPartnerByCode(String partnerCode);

}