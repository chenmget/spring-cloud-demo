package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.BrandActivityReq;
import com.iwhalecloud.retail.goods2b.dto.req.BrandGetReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class BrandServiceImplTest {

    @Autowired
    private BrandService brandService;

    @Test
    public void getBrand() {
        BrandGetReq req = new BrandGetReq();
        req.setBrandCode("20");
        ResultVO resultVO =  brandService.getBrand(req);
        System.out.println(JSON.toJSON(resultVO));
    }

    @Test
    public void test(){
        BrandActivityReq brandActivityReq = new BrandActivityReq();
        brandActivityReq.setBrandId("1068723084526751745");
        brandActivityReq.setRegionId("430100");
        brandActivityReq.setLanId("731");
        brandActivityReq.setSupplierId("11");
        ResultVO<Page<ActivityGoodsDTO>> listResultVO = brandService.listBrandActivityGoodsId(brandActivityReq);
        System.out.println(listResultVO.getResultData().getRecords());
    }
}
