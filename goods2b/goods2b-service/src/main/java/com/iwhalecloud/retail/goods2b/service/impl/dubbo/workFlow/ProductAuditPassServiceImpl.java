package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.req.ProductAuditStateUpdateReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.goods2b.service.dubbo.workFlow.ProductAuditPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Service
public class ProductAuditPassServiceImpl implements ProductAuditPassService {
    @Autowired
    private ProductService productService;
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("ProductAuditPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null||StringUtils.isEmpty(params.getBusinessId()) ) {

            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }

        ProductAuditStateUpdateReq req = new ProductAuditStateUpdateReq();
        req.setProductBaseId(params.getBusinessId());
        //审核通过
        req.setAuditState(ProductConst.AuditStateType.AUDIT_PASS.getCode());

        req.setStatus(ProductConst.StatusType.EFFECTIVE.getCode());

        req.setAttrValue10(ProductConst.attrValue10.EFFECTIVE.getCode());


        return productService.updateAuditState(req);
    }
}
