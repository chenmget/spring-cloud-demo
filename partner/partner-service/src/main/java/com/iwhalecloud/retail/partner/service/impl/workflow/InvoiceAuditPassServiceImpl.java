package com.iwhalecloud.retail.partner.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.ParInvoiceConst;
import com.iwhalecloud.retail.partner.dto.req.InvoiceAddReq;
import com.iwhalecloud.retail.partner.service.InvoiceService;
import com.iwhalecloud.retail.partner.service.workflow.InvoiceAuditPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class InvoiceAuditPassServiceImpl implements InvoiceAuditPassService {
    
    @Autowired
    private InvoiceService invoiceService;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("InvoiceAuditPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            log.info("InvoiceAuditPassServiceImpl.run LACK_OF_PARAM params={}", JSON.toJSONString(params));
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String invoiceId = params.getBusinessId();
        String vatInvoiceStatus = ParInvoiceConst.VatInvoiceStatus.AUDITED.getCode();
        InvoiceAddReq invoiceAddReq = new InvoiceAddReq();
        invoiceAddReq.setInvoiceId(invoiceId);
        invoiceAddReq.setVatInvoiceStatus(vatInvoiceStatus);
        return invoiceService.createParInvoice(invoiceAddReq);
    }
    
}
