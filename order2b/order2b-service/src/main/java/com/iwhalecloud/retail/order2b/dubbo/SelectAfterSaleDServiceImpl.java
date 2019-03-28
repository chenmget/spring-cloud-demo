package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleInfoDetailResp;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderApplyExportResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;
import com.iwhalecloud.retail.order2b.service.SelectAfterSaleOpenService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SelectAfterSaleDServiceImpl implements SelectAfterSaleOpenService {

    @Autowired
    private SelectAfterSaleOpenService selectAfterSaleOpenService;

    @Override
    public ResultVO<IPage<AfterSaleResp>> selectApply(SelectAfterSalesReq req) {
        return selectAfterSaleOpenService.selectApply(req);
    }

    @Override
    public ResultVO<IPage<AfterSaleResp>> selectHandler(SelectAfterSalesReq req) {
        return selectAfterSaleOpenService.selectHandler(req);
    }

    @Override
    public ResultVO<IPage<AfterSaleResp>> selectManagerList(SelectAfterSalesReq req) {
        return selectAfterSaleOpenService.selectManagerList(req);
    }

    @Override
    public ResultVO<AfterSaleInfoDetailResp> detail(SelectAfterSalesReq req) {
        return selectAfterSaleOpenService.detail(req);
    }

    @Override
    public ResultVO<OrderApplyExportResp> orderApplyExport(SelectAfterSalesReq req) {
        return selectAfterSaleOpenService.orderApplyExport(req);
    }
}
