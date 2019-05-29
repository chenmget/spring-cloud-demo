package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.req.ProductAuditStateUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductBaseGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.goods2b.service.dubbo.workFlow.ProductAuditUnPassService;
import com.iwhalecloud.retail.goods2b.utils.ZopClientUtil;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Service
public class ProductAuditUnPassServiceImpl implements ProductAuditUnPassService {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductBaseService productBaseService;
    @Value("${zop.secret}")
    private String zopSecret;

    @Value("${zop.url}")
    private String zopUrl;
    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("ProductAuditUnPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null||StringUtils.isEmpty(params.getBusinessId())) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }

        ProductAuditStateUpdateReq req = new ProductAuditStateUpdateReq();
        req.setProductBaseId(params.getBusinessId());
        //审核不通过
        req.setAuditState(ProductConst.AuditStateType.AUDIT_UN_PASS.getCode());

        req.setAttrValue10(ProductConst.attrValue10.NOTPASS.getCode());
        //固网产品需要提交串码到itms
//        ProductBaseGetByIdReq productBaseGetByIdReq = new ProductBaseGetByIdReq();
//        productBaseGetByIdReq.setProductBaseId(params.getBusinessId());
//        ResultVO<ProductBaseGetResp> productBaseGetRespResultVO = productBaseService.getProductBase(productBaseGetByIdReq);
//        if(productBaseGetRespResultVO.isSuccess() && null!=productBaseGetRespResultVO.getResultData()){
//            ProductBaseGetResp productBaseGetResp = productBaseGetRespResultVO.getResultData();
//            String isInspection = productBaseGetResp.getIsInspection();
//            if(StringUtils.isNotEmpty(isInspection) && ProductConst.isInspection.YES.getCode().equals(isInspection)){
//                String SerialCode = productBaseGetResp.getParam20();
//                if(StringUtils.isEmpty(SerialCode)){
//                    ResultVO.error("固网产品必须录入串码");
//                }
//
//                String b = "";
//                String callUrl = "";
//                Map request = new HashMap<>();
//                try {
//                    b = ZopClientUtil.zopService(callUrl, zopUrl, request, zopSecret);
//                } catch (Exception e) {
//                    log.error(e.getMessage());
//                }
//            }
//        }

        return productService.updateAuditState(req);
    }


}
