package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.InvoiceAddReq;
import com.iwhalecloud.retail.partner.dto.req.InvoicePageReq;
import com.iwhalecloud.retail.partner.dto.resp.InvoiceAddResp;
import com.iwhalecloud.retail.partner.dto.resp.InvoicePageResp;
import com.iwhalecloud.retail.partner.service.InvoiceService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author li.xinhang
 * @Date 2019/2/22
 *
 * 商家发票
 **/
@RestController
@RequestMapping("/api/b2b/partnerInvoice")
@Slf4j
@Api(value="商家发票:", tags={"商家发票:InvoiceB2BController"})
public class InvoiceB2BController {

    @Reference
    private InvoiceService invoiceService;

    @ApiOperation(value = "根据条件查询商家专票", notes = "条件分页查询")
            @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/queryParInvoice")
    @UserLoginToken
    public ResultVO<Page<InvoicePageResp>> pageInvoiceByMerchantId(
            @RequestParam(value = "pageNo") Integer pageNo,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "merchantId") String merchantId,
            @RequestParam(value = "invoiceType") String invoiceType){
        InvoicePageReq invoicePageReq = new InvoicePageReq();
        invoicePageReq.setPageNo(pageNo);
        invoicePageReq.setPageSize(pageSize);
        boolean isAdmin = UserContext.isAdminType();
        // 是管理员
        if (isAdmin) {
            UserDTO user = UserContext.getUser();
            if(Objects.isNull(user)) {
                return ResultVO.error("您尚未登陆，请登录");
            }
            invoicePageReq.setMerchantId(merchantId);
        } else {
            // 不是管理员
            invoicePageReq.setMerchantId(UserContext.getMerchantId());
        }
        invoicePageReq.setInvoiceType(invoiceType);
        return invoiceService.pageInvoiceByMerchantId(invoicePageReq);
    }

    @ApiOperation(value = "创建商家发票", notes = "创建商家发票")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/createParInvoice")
    @UserLoginToken
    public ResultVO<InvoiceAddResp> createParInvoice(@RequestBody InvoiceAddReq req) {
        log.info("InvoiceB2BController createParInvoice req={} ", JSON.toJSON(req));
        UserDTO userDTO = UserContext.getUser();
        req.setUserId(userDTO.getUserId());
        req.setUserName(userDTO.getUserName());
        req.setLanId(userDTO.getLanId());
        req.setRegionId(userDTO.getRegionId());
        return invoiceService.createParInvoice(req);
    }

    @ApiOperation(value = "修改商家发票", notes = "修改商家发票")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/updateParInvoice")
    @UserLoginToken
    public ResultVO<InvoiceAddResp> updateParInvoice(@RequestBody InvoiceAddReq req) {
        log.info("InvoiceB2BController updateParInvoice req={} ", JSON.toJSON(req));
        UserDTO userDTO = UserContext.getUser();
        req.setUserId(userDTO.getUserId());
        req.setUserName(userDTO.getUserName());
        req.setLanId(userDTO.getLanId());
        req.setRegionId(userDTO.getRegionId());
        return invoiceService.createParInvoice(req);
    }

//    @ApiOperation(value = "审核发票", notes = "审核发票")
//    @ApiResponses({
//            @ApiResponse(code=400,message="请求参数没填好"),
//            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
//    })
//    @PostMapping(value="/auditingParInvoice")
//    public ResultVO<InvoiceAddResp> auditingParInvoice(@RequestParam String invoiceId) {
//        log.info("InvoiceB2BController auditingParInvoice req={} ", invoiceId);
//        InvoiceDTO invoiceDTO = new InvoiceDTO();
//        invoiceDTO.setInvoiceId(invoiceId);
//        invoiceDTO.setVatInvoiceStatus(ParInvoiceConst.VatInvoiceStatus.AUDITED.getCode());
//        return invoiceService.createParInvoice(invoiceDTO);
//    }

//    @ApiOperation(value = "审核发票", notes = "审核发票")
//    @ApiResponses({
//            @ApiResponse(code=400,message="请求参数没填好"),
//            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
//    })
//    @PostMapping(value="/auditingParInvoice")
//    @UserLoginToken
//    public ResultVO audInvoice(String invoiceId) {
//        log.info("InvoiceB2BController audInvoice ");
//        String userId = UserContext.getUserId();
//        return invoiceService.audInvoice(userId,invoiceId);
//    }

    @ApiOperation(value = "发票详情查询", notes = "发票详情查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/queryParInvoiceInfo")
    public ResultVO<InvoicePageResp> queryParInvoiceInfo(@RequestParam String invoiceId) {
        log.info("InvoiceB2BController auditingParInvoice req={} ", invoiceId);
        return invoiceService.queryParInvoiceInfo(invoiceId);
    }
    
}
