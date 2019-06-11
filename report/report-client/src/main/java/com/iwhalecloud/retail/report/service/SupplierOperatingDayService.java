package com.iwhalecloud.retail.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.SummarySaleBySupplierPageReq;
import com.iwhalecloud.retail.report.dto.response.SummarySaleBySupplierPageResp;

/**
 * @author wenlong.zhong
 * @date 2019/6/10
 */
public interface SupplierOperatingDayService {

    /**
     * 地包进销存 数据 汇总 分页查询（按地包商的维度）
     * @return
     */
    ResultVO<Page<SummarySaleBySupplierPageResp>> pageSummarySaleBySupplier(SummarySaleBySupplierPageReq req);

}
