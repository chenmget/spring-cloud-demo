package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.FileAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.OldProductBaseUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductBaseUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseLightResp;
import com.iwhalecloud.retail.goods2b.manager.ProductBaseManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class ProductBaseServiceImplTest {

    @Autowired
    private ProductBaseService productBaseService;

    @Autowired
    private ProductBaseManager productBaseManager;

    @Test
    public void updateAvgApplyPrice() {
        ProductBaseUpdateReq req = new ProductBaseUpdateReq();
        req.setProductBaseId("100000150");
        ResultVO<Boolean> resultVO = productBaseService.updateAvgApplyPrice(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void update() {
        ProductBaseUpdateReq req = new ProductBaseUpdateReq();
        OldProductBaseUpdateReq oldreq =new OldProductBaseUpdateReq();
        oldreq.setSallingPoint("小米卖点测试");
        oldreq.setProductName("小米卖点测试");
        List<ProductUpdateReq> oldproductUpdateReqs = new ArrayList<>();
        ProductUpdateReq oldupdatereq = new ProductUpdateReq();
        oldupdatereq.setProductId("12335343");
        oldupdatereq.setSn("1111111111111111111");
        oldupdatereq.setCost(12000D);
        oldproductUpdateReqs.add(oldupdatereq);
        List<FileAddReq> fileAddReqs = new ArrayList<>();
        FileAddReq fileAddReq = new FileAddReq();
        fileAddReq.setFileUrl("http://134.176.97.50:8081/group1/M00/00/0C/hrBhM1yWMVSAOYO9ABWrW3y72_U220.mp4");
        fileAddReq.setSubType("7");
        fileAddReq.setThumbnailUrl("http://134.176.97.50:8081/group1/M00/00/0C/hrBhMlyWMVSAAJcZAAAcob6DVNg540.png");
        fileAddReqs.add(fileAddReq);
        oldupdatereq.setFileAddReqs(fileAddReqs);
        oldreq.setProductUpdateReqs(oldproductUpdateReqs);
        req.setOldProductBaseUpdateReq(oldreq);

        req.setProductBaseId("12335342");
        req.setProductName("小米卖点测试");
        req.setSallingPoint("小米卖点测试");
        List<ProductUpdateReq> newproductUpdateReqs = new ArrayList<>();
        ProductUpdateReq newupdatereq = new ProductUpdateReq();
        newupdatereq.setProductId("12335343");
        newupdatereq.setSn("1111111111111111111");
        newupdatereq.setCost(13000D);

        List<FileAddReq> nfileAddReqs = new ArrayList<>();
        FileAddReq nfileAddReq = new FileAddReq();
        nfileAddReq.setFileUrl("http://134.176.97.50:8081/group1/M00/00/0C/hrBhM1yWMVSAOYO9ABWrW3y72_U220.mp4");
        nfileAddReq.setSubType("7");
        nfileAddReq.setThumbnailUrl("http://134.176.97.50:8081/group1/M00/00/0C/hrBhMlyWMVSAAJcZAAAcob6DVNg540.png");
        nfileAddReqs.add(nfileAddReq);
        newupdatereq.setFileAddReqs(nfileAddReqs);
        newproductUpdateReqs.add(newupdatereq);
        req.setProductUpdateReqs(newproductUpdateReqs);

        ResultVO<Integer> resultVO = productBaseService.updateProductBase(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void updatetest() {

       List<String> list = productBaseService.getSeq(5).getResultData();
        System.out.println(list);
    }

    @Test
    public void getDistinctUnitType() {
        List<String> list = productBaseManager.getDistinctUnitType("201903142030001", "1105424939502649346");
        System.out.println(list);
    }

    @Test
    public void getProductBaseByProductId() {
        ProductBaseLightResp resp = productBaseManager.getProductBaseByProductId("100000152");
        System.out.println(resp);
    }
}