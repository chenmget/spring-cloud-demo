package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.req.ProductAuditStateUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductBaseGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.goods2b.service.dubbo.workFlow.ProductAuditPassService;
import com.iwhalecloud.retail.goods2b.utils.ZopClientUtil;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Service
public class ProductAuditPassServiceImpl implements ProductAuditPassService {
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
        //固网产品需要提交串码到itms
        ProductBaseGetByIdReq productBaseGetByIdReq = new ProductBaseGetByIdReq();
        productBaseGetByIdReq.setProductBaseId(params.getBusinessId());
        ResultVO<ProductBaseGetResp> productBaseGetRespResultVO = productBaseService.getProductBase(productBaseGetByIdReq);
        if(productBaseGetRespResultVO.isSuccess() && null!=productBaseGetRespResultVO.getResultData()){
            ProductBaseGetResp productBaseGetResp = productBaseGetRespResultVO.getResultData();
            String isInspection = productBaseGetResp.getIsInspection();
            if(StringUtils.isNotEmpty(isInspection) && ProductConst.isInspection.YES.getCode().equals(isInspection)){
                String serialCode = productBaseGetResp.getParam20();  //串码  xxxx-1234556612
//                String param = productBaseGetResp.getParam19(); //附加参数  city_code=731# warehouse=12#source=1#factory=厂家
                String userName = productBaseGetResp.getParam18();  //login_name
                if(StringUtils.isNotEmpty(serialCode) && StringUtils.isNotEmpty(userName)){
                    if(serialCode.indexOf(",")>-1){
                        String[] serialCodes = serialCode.split(",");
                        for(int i=0;i<serialCodes.length;i++){
                            this.pushItms(serialCodes[i],userName,"ITMS_DELL","");
                        }
                    }else{
                        this.pushItms(serialCode,userName,"ITMS_DELL","");
                    }

//                    String b = "";
//                    String callUrl = "ord.operres.OrdInventoryChange";
//                    Map request = new HashMap<>();
//                    request.put("deviceId",serialCode);
//                    request.put("userName",userName);
//                    request.put("code","ITMS_DELL");
//                    request.put("params","");
//                    try {
//                        b = ZopClientUtil.zopService(callUrl, zopUrl, request, zopSecret);
//                        Map parseObject = JSON.parseObject(b, new TypeReference<HashMap>(){});
//                        String body = String.valueOf(parseObject.get("Body"));
//                        Map parseObject2 = JSON.parseObject(body, new TypeReference<HashMap>(){});
//                        String inventoryChangeResponse = String.valueOf(parseObject2.get("inventoryChangeResponse"));
//                        Map parseObject3 = JSON.parseObject(inventoryChangeResponse, new TypeReference<HashMap>(){});
//                        String inventoryChangeReturn = String.valueOf(parseObject3.get("inventoryChangeReturn"));
//                        if("-1".equals(inventoryChangeReturn)){
//                            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "串码推送ITMS(删除)失败");
//                        }else if("1".equals(inventoryChangeReturn)){
//                            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "串码推送ITMS(删除)已经存在");
//                        }
//                    } catch (Exception e) {
//                        log.error(e.getMessage());
//                    }
                }
            }
        }

        return productService.updateAuditState(req);
    }

    private void pushItms(String deviceId,String userName,String code,String params) {
        String b = "";
        String callUrl = "ord.operres.OrdInventoryChange";
        Map request = new HashMap<>();
        request.put("deviceId", deviceId);
        request.put("userName", userName);
        request.put("code", code);
        request.put("params", params);
        try {
            b = ZopClientUtil.zopService(callUrl, zopUrl, request, zopSecret);
            if (StringUtils.isNotEmpty(b)) {
                Map parseObject = JSON.parseObject(b, new TypeReference<HashMap>() {
                });
                String body = String.valueOf(parseObject.get("Body"));
                Map parseObject2 = JSON.parseObject(body, new TypeReference<HashMap>() {
                });
                String inventoryChangeResponse = String.valueOf(parseObject2.get("inventoryChangeResponse"));
                Map parseObject3 = JSON.parseObject(inventoryChangeResponse, new TypeReference<HashMap>() {
                });
                String inventoryChangeReturn = String.valueOf(parseObject3.get("inventoryChangeReturn"));
                if ("-1".equals(inventoryChangeReturn)) {
                    throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "串码推送ITMS(删除)失败");
                } else if ("1".equals(inventoryChangeReturn)) {
                    throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "串码推送ITMS(删除)已经存在");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
