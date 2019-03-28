package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.service.dubbo.workFlow.GoodsAuditPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class GoodsAuditPassServiceImplTest {

    @Autowired
    private GoodsAuditPassService goodsAuditPassService;

    @Test
    public void run() {
        InvokeRouteServiceRequest request = new InvokeRouteServiceRequest();
        request.setBusinessId("1085135518260977666");
        ResultVO resultVO = goodsAuditPassService.run(request);
        System.out.println(resultVO.isSuccess());
    }
}