package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.AdminResourceInstDelReq;
import com.iwhalecloud.retail.warehouse.dto.request.InventoryChangeReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.manager.CallService;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.warehouse.util.ZopClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AdminResourceInstServiceImpl implements AdminResourceInstService {

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private SupplierResourceInstService supplierResourceInstService;

    @Reference
    private MerchantResourceInstService merchantResourceInstService;

    @Autowired
    private Constant constant;
    
    @Autowired
    private CallService callService;
    
    @Autowired
    private ResourceInstManager resourceInstManager;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Value("${zop.secret}")
    private String zopSecret;

    @Value("${zop.url}")
    private String zopUrl;
    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("AdminResourceInstServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return resourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("AdminResourceInstServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        // 管理员只能给厂商和零售商增加串码
        MerchantGetReq merchantGetReq = new MerchantGetReq();
        merchantGetReq.setMerchantId(req.getMktResStoreId());
        ResultVO<MerchantDTO> merchantResultVO = resouceStoreService.getMerchantByStore(req.getMktResStoreId());
        log.info("AdminResourceInstServiceImpl.addResourceInst merchantService.getMerchantDetail req={},resp={}", JSON.toJSONString(merchantGetReq), JSON.toJSONString(merchantResultVO));
        if (!merchantResultVO.isSuccess() || null == merchantResultVO.getResultData()) {
            return ResultVO.error(constant.getCannotGetMerchantMsg());
        }
        String merchantType = merchantResultVO.getResultData().getMerchantType();
        // 管理员添加串码有传目标仓库Id
        req.setDestStoreId(req.getMktResStoreId());
        if (PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(merchantType)) {
            req.setMerchantId(merchantResultVO.getResultData().getMerchantId());
            return merchantResourceInstService.addResourceInstByAdmin(req);
        }else if(PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(merchantType) || PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equals(merchantType)) {
            req.setMerchantId(merchantResultVO.getResultData().getMerchantId());
            return supplierResourceInstService.addResourceInstByAdmin(req);
        }else {
            return ResultVO.error("用户类型不正确");
        }
    }

    @Override
    public ResultVO updateResourceInstByIds(AdminResourceInstDelReq req) {
        log.info("AdminResourceInstServiceImpl.updateResourceInstByIds req={}", JSON.toJSONString(req));
        return resourceInstService.updateResourceInstByIds(req);
    }

    @Override
	public ResultVO inventoryChange(InventoryChangeReq req) {
		log.info("AdminResourceInstOpenServiceImpl.inventoryChange req={}", JSON.toJSONString(req));
		
//		InventoryChangeResp inventoryChangeResp = new InventoryChangeResp();
		String result = "";
		List<ResourceInstDTO> resourceInstList = resourceInstManager.listInstsByNbr(req.getDeviceId());
		if(resourceInstList.size()<=0 || null == resourceInstList){
			return ResultVO.error("串码不在库中");
		}
        String b = "";
        Map request = new HashMap<>();
        request.put("deviceId",req.getDeviceId());
        request.put("userName",req.getUserName());
        request.put("code",req.getCode());
        request.put("params",req.getParams());
        String callUrl = "ord.operres.OrdInventoryChange";
		try {
            b = ZopClientUtil.zopService(callUrl, zopUrl, request, zopSecret);
            Map parseObject = JSON.parseObject(b, new TypeReference<HashMap>(){});
            String body = String.valueOf(parseObject.get("Body"));
            Map parseObject2 = JSON.parseObject(body, new TypeReference<HashMap>(){});
            String inventoryChangeResponse = String.valueOf(parseObject2.get("inventoryChangeResponse"));
            Map parseObject3 = JSON.parseObject(inventoryChangeResponse, new TypeReference<HashMap>(){});
            String inventoryChangeReturn = String.valueOf(parseObject3.get("inventoryChangeReturn"));

            if("0".equals(inventoryChangeReturn)){
                log.info("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed inventoryChangeReturn={}", inventoryChangeReturn);
                return ResultVO.success("串码推送ITMS(添加)成功");
            }else if("1".equals(inventoryChangeReturn)){
                log.info("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed inventoryChangeReturn={}", inventoryChangeReturn);
                return ResultVO.error("串码推送ITMS(添加)已经存在");
            }else{
                log.info("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed inventoryChangeResponse={}", JSON.toJSONString(inventoryChangeResponse));
                return ResultVO.error("串码推送ITMS(添加)失败");
            }

//			result = callService.postInvenChangeToWebService(req);
//			inventoryChangeResp.setResult(result);
		} catch (Exception e) {
			log.error("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed req={}", JSON.toJSONString(req));
			return ResultVO.error("AdminResourceInstOpenServiceImpl.inventoryChange postWebServiceFailed");
		}
//		return ResultVO.success(result);
	}
    
    
}
