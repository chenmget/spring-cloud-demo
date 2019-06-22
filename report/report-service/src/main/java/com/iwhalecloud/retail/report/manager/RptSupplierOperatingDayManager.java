package com.iwhalecloud.retail.report.manager;

import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;
import com.iwhalecloud.retail.report.mapper.RptSupplierOperatingDayMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RptSupplierOperatingDayManager {

    @Resource
    private RptSupplierOperatingDayMapper rptSupplierOperatingDayMapper;

    public String hqmaxItemId() {
        return rptSupplierOperatingDayMapper.hqmaxItemId();
    }

    public void getDataForRptSupplierOperatingDay(RptSupplierOperatingDayReq req) {
        rptSupplierOperatingDayMapper.getDataForRptSupplierOperatingDay(req);
    }

    public List<ParMerchantResp> hqParMerchant() {
        return rptSupplierOperatingDayMapper.hqParMerchant();
    }

    //第二步：通过商家ID获取供应商仓库
    public String hqMktResStore(MktResInstEventReq req) {
        return rptSupplierOperatingDayMapper.hqMktResStore(req);

    }

    //库存的
    public List<MktResInstResq> hqMktResInst(MktResInstEventReq req) {
        return rptSupplierOperatingDayMapper.hqMktResInst(req);
    }

    //入库的
    public List<MktResEventruchu> hqmktResEventru(MktResInstEventReq req) {
        return rptSupplierOperatingDayMapper.hqmktResEventru(req);
    }

    //出库的
    public List<MktResEventruchu> hqmktResEventchu(MktResInstEventReq req) {
        return rptSupplierOperatingDayMapper.hqmktResEventchu(req);
    }
}
