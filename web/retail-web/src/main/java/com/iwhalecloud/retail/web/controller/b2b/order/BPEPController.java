package com.iwhalecloud.retail.web.controller.b2b.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.OrderPayInfoResp;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.AsynNotifyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OrderPayInfoReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.ToPayReq;
import com.iwhalecloud.retail.order2b.service.BestPayEnterprisePaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/b2b/order/pay")
@Slf4j
public class BPEPController {

    @Reference
    private BestPayEnterprisePaymentService bestPayEnterprisePaymentService;

    /**
     *  去支付
     */
    @RequestMapping(value="/toPay",method = RequestMethod.POST)
    public ResultVO toPay(@RequestBody ToPayReq req){
        return bestPayEnterprisePaymentService.toPay(req);
    }

    /**
     *
     * 翼支付支付结果回调并记录支付支付结果
     * @param ORGLOGINCODE
     * @param PLATCODE
     * @param CUSTCODE
     * @param ORDERID
     * @param TRSSEQ
     * @param ORDERAMOUNT
     * @param FEE
     * @param TRANSDATE
     * @param ORDERSTATUS
     * @param BANKCODE
     * @param PERENTFLAG
     * @param COMMENT1
     * @param COMMENT2
     * @param SIGNSTR
     * @param IOUSAMOUNT
     * @param ORDERTYPE
     * @return
     */
    @RequestMapping(value="/asynNotify",method = RequestMethod.POST)
    public ResultVO asynNotify(@RequestParam(value = "ORGLOGINCODE", required = false) String ORGLOGINCODE,
                                               @RequestParam(value = "PLATCODE", required = false) String PLATCODE,
                                               @RequestParam(value = "CUSTCODE", required = false) String CUSTCODE,
                                               @RequestParam(value = "ORDERID", required = false) String ORDERID,
                                               @RequestParam(value = "TRSSEQ", required = false) String TRSSEQ,
                                               @RequestParam(value = "ORDERAMOUNT", required = false) String ORDERAMOUNT,
                                               @RequestParam(value = "FEE", required = false) String FEE,
                                               @RequestParam(value = "TRANSDATE", required = false) String TRANSDATE,
                                               @RequestParam(value = "ORDERSTATUS", required = false) String ORDERSTATUS,
                                               @RequestParam(value = "BANKCODE", required = false) String BANKCODE,
                                               @RequestParam(value = "PERENTFLAG", required = false) String PERENTFLAG,
                                               @RequestParam(value = "COMMENT1", required = false) String COMMENT1,
                                               @RequestParam(value = "COMMENT2", required = false) String COMMENT2,
                                               @RequestParam(value = "SIGNSTR", required = false) String SIGNSTR,
                                               @RequestParam(value = "IOUSAMOUNT", required = false) String IOUSAMOUNT,
                                               @RequestParam(value = "ORDERTYPE", required = false) String ORDERTYPE){
        AsynNotifyReq req = new AsynNotifyReq();
        req.setORGLOGINCODE(ORGLOGINCODE);
        req.setPLATCODE(PLATCODE);
        req.setCUSTCODE(CUSTCODE);
        req.setORDERID(ORDERID);
        req.setTRSSEQ(TRSSEQ);
        req.setORDERAMOUNT(ORDERAMOUNT);
        req.setFEE(FEE);
        req.setTRANSDATE(TRANSDATE);
        req.setORDERSTATUS(ORDERSTATUS);
        req.setBANKCODE(BANKCODE);
        req.setPERENTFLAG(PERENTFLAG);
        req.setCOMMENT1(COMMENT1);
        req.setCOMMENT2(COMMENT2);
        req.setSIGNSTR(SIGNSTR);
        req.setIOUSAMOUNT(IOUSAMOUNT);
        req.setORDERTYPE(ORDERTYPE);
        log.info("BPEPController.asynNotify req={}", JSON.toJSONString(req));
        ResultVO resultVO = bestPayEnterprisePaymentService.asynNotify(req);
        log.info("BPEPController.asynNotify resp={}", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 订单支付状态查询即展示
     * @param req
     * @return
     */
    @ApiOperation(value = "订单支付状态查询", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="qryOrderPayInfo")
    public ResultVO<OrderPayInfoResp> qryOrderPayInfo(@RequestBody OrderPayInfoReq req){
        return bestPayEnterprisePaymentService.qryOrderPayInfo(req);
    }
}
