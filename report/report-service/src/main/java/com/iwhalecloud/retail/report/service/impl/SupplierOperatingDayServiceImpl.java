package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.SummarySaleBySupplierPageReq;
import com.iwhalecloud.retail.report.dto.response.SummarySaleBySupplierPageResp;
import com.iwhalecloud.retail.report.manager.SupplierOperatingDayManager;
import com.iwhalecloud.retail.report.service.SupplierOperatingDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wenlong.zhong
 * @date 2019/6/10
 */
@Service
@Slf4j
public class SupplierOperatingDayServiceImpl implements SupplierOperatingDayService {

    @Autowired
    private SupplierOperatingDayManager supplierOperatingDayManager;

    /**
     * 地包进销存 数据 汇总 分页查询（按地包商的维度）
     * @return
     */
    @Override
    public ResultVO<Page<SummarySaleBySupplierPageResp>> pageSummarySaleBySupplier(SummarySaleBySupplierPageReq req) {
        log.info("SupplierOperatingDayServiceImpl.pageSummarySaleBySupplier(), input：req={} ", JSON.toJSONString(req));
        Page<SummarySaleBySupplierPageResp> respPage = supplierOperatingDayManager.pageSummarySaleBySupplier(req);
        log.info("SupplierOperatingDayServiceImpl.pageSummarySaleBySupplier(), output：list={} ", JSON.toJSONString(respPage.getRecords()));
        return ResultVO.success(respPage);
    }

}
