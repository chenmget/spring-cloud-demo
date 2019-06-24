package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.BrandActivityReq;
import com.iwhalecloud.retail.goods2b.dto.req.BrandGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.BrandPageReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void page() {
        BrandPageReq req = new BrandPageReq();
        req.setBrandIdList(Lists.newArrayList("100004412","1105409236565344258","1105410392788156417","1105424379592425473"));
        req.setPageSize(3);
        ResultVO resultVO =  brandService.pageBrandFileUrl(req);
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
