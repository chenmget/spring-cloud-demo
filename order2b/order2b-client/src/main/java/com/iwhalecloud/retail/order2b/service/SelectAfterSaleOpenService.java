package com.iwhalecloud.retail.order2b.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleInfoDetailResp;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderApplyExportResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;

public interface SelectAfterSaleOpenService {

    /**
     * 我申请的售后
     */
    ResultVO<IPage<AfterSaleResp>> selectApply(SelectAfterSalesReq req);

    /**
     * 我要处理的售后
     */
    ResultVO<IPage<AfterSaleResp>> selectHandler(SelectAfterSalesReq req);

    /**
     * 我要处理的售后
     */
    ResultVO<IPage<AfterSaleResp>> selectManagerList(SelectAfterSalesReq req);

    /**
     * 详情
     */
    ResultVO<AfterSaleInfoDetailResp> detail(SelectAfterSalesReq req);

    /**
     * 导出售后单
     */
    ResultVO<OrderApplyExportResp> orderApplyExport(SelectAfterSalesReq req);

}
