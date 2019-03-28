package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.dto.response.CloseOrderApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.CloseOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyGetReq;
import com.iwhalecloud.retail.order2b.service.CloseOrderOpenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 关闭订单测试类
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月25日
 */
public class CloseOrderServiceTest extends TestBase {

    @Autowired
    private CloseOrderOpenService closeOrderOpenService;

    @Test
    public void apply_close_order_test() {
        CloseOrderReq req = new CloseOrderReq();
        req.setOrderId("201903263610111240");
        req.setLanId("731");
        req.setQuestionDesc("申请关闭");
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add("http://test");
        imgUrls.add("http://test1");
        req.setRefundImgUrls(imgUrls);
        req.setUserId("1077839559879852033");
        SourceFromContext.setSourceFrom("YHJ");
        closeOrderOpenService.applyCloseOrder(req);
    }

    @Test
    public void query_close_order_apply_test() {
        OrderApplyGetReq req = new OrderApplyGetReq();
        req.setLanId("731");
        req.setOrderId("201903263610111240");
        SourceFromContext.setSourceFrom("YHJ");
        Order2bContext.setDBLanId(req);
        ResultVO<CloseOrderApplyResp> resultVO = closeOrderOpenService.queryCloseOrderApply(req);
        System.out.println("结果为：" + resultVO.toString());
    }

    @Test
    public void agree_close_order_test() {
        CloseOrderReq req = new CloseOrderReq();
        req.setLanId("731");
        req.setOrderId("201903263610111240");
        SourceFromContext.setSourceFrom("YHJ");
        Order2bContext.setDBLanId(req);
        closeOrderOpenService.agreeClose(req);
    }

    @Test
    public void disagree_close_order_test() {
        CloseOrderReq req = new CloseOrderReq();
        req.setLanId("731");
        req.setOrderId("201903263610111240");
        SourceFromContext.setSourceFrom("YHJ");
        Order2bContext.setDBLanId(req);
        closeOrderOpenService.disagreeClose(req);
    }

}
