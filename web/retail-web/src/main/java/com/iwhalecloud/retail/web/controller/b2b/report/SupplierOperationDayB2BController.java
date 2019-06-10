package com.iwhalecloud.retail.web.controller.b2b.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesDetailListReq;
import com.iwhalecloud.retail.report.dto.SupplierOperatingDayDTO;
import com.iwhalecloud.retail.report.dto.request.SupplierOperatingDayPageReq;
import com.iwhalecloud.retail.report.service.SupplierOperatingDayService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author wenlong.zhong
 * @date 2019/6/10
 */
@RestController
@RequestMapping("/api/b2b/supplierOperatingDay")
@Slf4j
@Api(value = "地包进销存 数据 控制器", tags = {"地包进销存 数据 控制器"})
public class SupplierOperationDayB2BController {

    @Reference
    private SupplierOperatingDayService supplierOperatingDayService;

    @ApiOperation(value = "获取 地包进销存 数据 分页 列表接口", notes = "获取 地包进销存 数据 分页 列表接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/page")
    public ResultVO<Page<SupplierOperatingDayDTO>> page(@RequestBody @ApiParam(value = "获取 地包进销存 数据 分页 列表接口参数", required = true) SupplierOperatingDayPageReq req) {
        return supplierOperatingDayService.page(req);
    }

}
