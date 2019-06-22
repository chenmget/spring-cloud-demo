package com.iwhalecloud.retail.partner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.SupplyProductRelDTO;
import com.iwhalecloud.retail.partner.dto.resp.SupplyProductQryResp;

public interface SupplyProductRelService {
    /**
     * @Auther: lin.wh
     * @Date: 2018/11/14
     * @Description: 分页查询可供产品
     */
    Page<SupplyProductQryResp> querySupplyProductRel(SupplyProductRelDTO dto);

    /**
     * @Auther: lin.wh
     * @Date: 2018/11/14
     * @Description: 可供产品绑定
     */
    int bindSupplyProductRel(SupplyProductRelDTO dto);

    /**
     * @Auther: lin.wh
     * @Date: 2018/11/14
     * @Description: 可供产品解除绑定
     */
    int unBindSupplyProductRel(SupplyProductRelDTO dto);
}