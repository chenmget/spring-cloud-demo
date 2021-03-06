package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.service.NewParMerchAddProdService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/newMerchant")
public class NewParMerchAddProdController {

    @Reference
    private NewParMerchAddProdService newParMerchAddProdService;

    @Reference
    private ProductService productService;

    @ApiOperation(value = "编辑选择零售商时设置默认赋权：a、所有机型经营权限； b、所有地市和所有机型的调拨权限", notes = "编辑选择零售商赋所有机型经营权限")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数不全"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/addProd")
    public ResultVO<Integer> addProd(@RequestBody LSSAddControlReq req) {

        // 新逻辑 zhongwenlong2019.06.27
        return newParMerchAddProdService.addRetailerDefaultRule(req.getMerchantId());

    }
}
