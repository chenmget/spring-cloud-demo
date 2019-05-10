package com.iwhalecloud.retail.web.controller.b2b.cgmanage;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.JyPurApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PriCityManagerResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.WfTaskResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.service.JyPurApplyService;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liweisong
 * @date 2019-04-16
 * 采购申请交易平台
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/jycgmanage")
public class JySearchApplyControll extends BaseController  {

	@Reference
    private JyPurApplyService jypurApplyService;
	
	@Reference
    private PurApplyService purApplyService;
	
	@ApiOperation(value = "查询采购申请单报表", notes = "查询采购申请单报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/jycgSearchApply")
    public ResultVO<Page<JyPurApplyResp>> jycgSearchApply(@RequestBody PurApplyReq req) {
		//采购单的时候点击查看采购申请单，传申请人ID，apply_code和apply_name项目名称查询
		String userId = UserContext.getUserId();
		PriCityManagerResp login = purApplyService.getLoginInfo(userId);
		String userType = login.getUserType();
		//传过来的APPLY_TYPE看
		if("4".equals(userType) || "4" == userType){//零售商只看自己的采购单
			req.setApplyMerchantId(userId);
		}
		return jypurApplyService.jycgSearchApply(req);
    }
	
}
