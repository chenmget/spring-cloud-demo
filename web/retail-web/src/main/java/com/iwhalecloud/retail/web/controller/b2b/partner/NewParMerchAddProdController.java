package com.iwhalecloud.retail.web.controller.b2b.partner;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.ManufacturerDTO;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerSaveReq;
import com.iwhalecloud.retail.partner.dto.resp.ProductIdListResp;
import com.iwhalecloud.retail.partner.service.ManufacturerService;
import com.iwhalecloud.retail.partner.service.NewParMerchAddProdService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/newMerchant")
public class NewParMerchAddProdController {

	@Reference
    private NewParMerchAddProdService newParMerchAddProdService;

    @ApiOperation(value = "编辑选择零售商赋所有机型经营权限", notes = "编辑选择零售商赋所有机型经营权限")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/addProd")
    public void addProd(@RequestBody LSSAddControlReq req){
    	req.setRuleType("1");
    	req.setTargetType("2");
//    	List<ProductIdListResp> targetIdList = newParMerchAddProdService.selectProductIdList();
    	List<String> targetIdList = newParMerchAddProdService.selectProductIdList();
    	req.setTargetIdList(targetIdList);
    	newParMerchAddProdService.addProd(req);
    }

}
