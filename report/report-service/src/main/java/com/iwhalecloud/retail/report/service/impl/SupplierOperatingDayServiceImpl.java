package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.SupplierOperatingDayDTO;
import com.iwhalecloud.retail.report.dto.request.SupplierOperatingDayPageReq;
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
     * 地包进销存 数据 分页查询
     * @return
     */
    @Override
    public ResultVO<Page<SupplierOperatingDayDTO>> page(SupplierOperatingDayPageReq req) {
        log.info("SupplierOperatingDayServiceImpl.page(), input：req={} ", JSON.toJSONString(req));
        Page<SupplierOperatingDayDTO> respPage = supplierOperatingDayManager.page(req);
        log.info("SupplierOperatingDayServiceImpl.page(), output：list={} ", JSON.toJSONString(respPage.getRecords()));
        return ResultVO.success(respPage);
    }

}
