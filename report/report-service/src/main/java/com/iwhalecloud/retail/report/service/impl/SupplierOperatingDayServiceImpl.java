package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.SupplierOperatingDayDTO;
import com.iwhalecloud.retail.report.dto.request.SummarySaleBySupplierPageReq;
import com.iwhalecloud.retail.report.dto.request.SupplierOperatingDayPageReq;
import com.iwhalecloud.retail.report.dto.response.SummarySaleBySupplierPageResp;
import com.iwhalecloud.retail.report.manager.SupplierOperatingDayManager;
import com.iwhalecloud.retail.report.service.SupplierOperatingDayService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
     * 地包进销存 数据 分页查询
     * @return
     */
//    @Override
//    public ResultVO<Page<SupplierOperatingDayDTO>> page(SupplierOperatingDayPageReq req) {
//        log.info("SupplierOperatingDayServiceImpl.page(), input：req={} ", JSON.toJSONString(req));
//        Page<SupplierOperatingDayDTO> respPage = supplierOperatingDayManager.page(req);
//        log.info("SupplierOperatingDayServiceImpl.page(), output：list={} ", JSON.toJSONString(respPage.getRecords()));
//        return ResultVO.success(respPage);
//    }

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
