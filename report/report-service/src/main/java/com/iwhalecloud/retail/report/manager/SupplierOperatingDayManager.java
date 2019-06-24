package com.iwhalecloud.retail.report.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.SummarySaleBySupplierPageReq;
import com.iwhalecloud.retail.report.dto.response.SummarySaleBySupplierPageResp;
import com.iwhalecloud.retail.report.mapper.SupplierOperatingDayMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class SupplierOperatingDayManager {
    @Resource
    private SupplierOperatingDayMapper supplierOperatingDayMapper;

    /**
     * 地包进销存 数据 汇总 分页查询（按地包商的维度）
     *
     * @return
     */
    public Page<SummarySaleBySupplierPageResp> pageSummarySaleBySupplier(SummarySaleBySupplierPageReq req) {
        Page<SummarySaleBySupplierPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<SummarySaleBySupplierPageResp> respPage = supplierOperatingDayMapper.pageSummarySaleBySupplier(page, req);
        return respPage;
    }
}
