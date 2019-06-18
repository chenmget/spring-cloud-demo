package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.req.RegionReq;
import com.iwhalecloud.retail.goods2b.entity.GoodsRegionRel;
import com.iwhalecloud.retail.goods2b.manager.GoodsRegionRelManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wenlong.zhong
 * @date 2019/6/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class GoodsRegionRelTest {

    @Autowired
    private GoodsRegionRelManager goodsRegionRelManager;

    /**
     * 添加商品发布地市关联记录
     */
    @Test
    public void saveGoodsRegionRel() {
        String goodsId = "111222";
        RegionReq regionReq = new RegionReq();
        regionReq.setRegionName("RegionId");
        regionReq.setRegionId("111222");
        regionReq.setLanId("111");
        regionReq.setOrgId("3333444");
        regionReq.setOrgName("3333444");
        // 添加商品发布地市关联记录(增加 org_id、org_name两个字段后）
        GoodsRegionRel goodsRegionRel = new GoodsRegionRel();
        BeanUtils.copyProperties(regionReq, goodsRegionRel);
        goodsRegionRel.setGoodsId(goodsId);
        int i = goodsRegionRelManager.saveGoodsRegionRel(goodsRegionRel);
        System.out.print(i);
    }
}
